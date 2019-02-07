package OntologyRetriever;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

import Tweet.CallForHelpTweet;
import Tweet.CasualtiesAndDamageTweet;
import Tweet.CautionAndAdviceTweet;
import Tweet.DonationTweet;

public class OntologyRetriever {
	OWLOntologyManager manager;
	OWLOntology filietOntology;
	File file;
	
	private static String BASE_IRI = "http://www.semanticweb.org/kylemchalebdelacruz/ontologies/2014/9/untitled-ontology-5#";
	private static String TWEET_CLASS_IRI = BASE_IRI + "Tweet";
	
	public OntologyRetriever() {}
	
	// RETRIEVER PRE-REQUISITE METHOD: Ontology Loader
	public void loadOntology() throws OWLOntologyCreationException {
		// Get hold of an ontology manager
		manager = OWLManager.createOWLOntologyManager();
		// This is the file for the Ontology
		file = new File("resource/FILIET_Ontology2.owl");
		// Now load the local copy
		filietOntology = manager.loadOntologyFromOntologyDocument(file);
		// Display the Name of the Loaded Ontology
		System.out.println("<+> FILIET ONTOLOGY MODULE: Loading Ontology...");
		System.out.println("    > Ontology loaded successfully: " + filietOntology);
	}
	
	// RETRIEVER PRE-REQUISITE METHOD: Ontology Remover
	public void removeOntologyFromManager() {
		manager.removeOntology(filietOntology);
	}
	
	// RETRIEVER PRE-REQUISITE METHOD: Ontology Content Retriever
	public RetrievedTweet getStoredTweets() {
		// PREPARE THE REASONER AND PREREQUISITE STUFF
		OWLDataFactory dataFactory = manager.getOWLDataFactory();
		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);
		OWLReasoner reasoner = reasonerFactory.createReasoner(filietOntology,config);

		OWLClass tweetClass = dataFactory.getOWLClass(IRI.create(TWEET_CLASS_IRI));
		
		// THIS IS THE LIST THAT CONTAINS THE INSTANCES OF THE TWEET CLASS
		NodeSet<OWLNamedIndividual> twIndividualsNodeSet = reasoner.getInstances(tweetClass, true);
		Set<OWLNamedIndividual> tw = twIndividualsNodeSet.getFlattened();
		ArrayList<OWLNamedIndividual> twIndividuals = new ArrayList<OWLNamedIndividual>(tw);
		
		// PREPARE ARRAYLISTS OF DIFFERENT CATEGORIES TO CONTAIN THE TWEETS
		ArrayList<CallForHelpTweet> retrievedCFHTweets = new ArrayList<CallForHelpTweet>();
		ArrayList<CasualtiesAndDamageTweet> retrievedCADTweets = new ArrayList<CasualtiesAndDamageTweet>();
		ArrayList<CautionAndAdviceTweet> retrievedCATweets = new ArrayList<CautionAndAdviceTweet>();
		ArrayList<DonationTweet> retrievedDTweets = new ArrayList<DonationTweet>();
		
		// ITERATE THROUGH THE LIST OF INSTANCES OF THE TWEET CLASS
		for(int x = 0; x < twIndividuals.size(); x++) {
			// GET THE DATA PROPERTIES OF THE TWEET INSTANCE
			ArrayList<DPVPair> tweetInfo = getDataPropertyValues(twIndividuals.get(x));
				
			// GET THE LOCATION AND TIMESTAMP INFO OF THE TWEET INSTANCE
			OWLObjectProperty hasThisInfo = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "has_this_information"));
			ArrayList<DPVPair> timestampInfo = new ArrayList<DPVPair>();
			ArrayList<DPVPair> locationInfo = new ArrayList<DPVPair>();
			for (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(twIndividuals.get(x), hasThisInfo).getFlattened()) { 
				if(ind.toStringID().contains("TSI")) {
					timestampInfo = getDataPropertyValues(ind);
				} else {
					locationInfo = getDataPropertyValues(ind);
				}
		    }
			
			// GET THE INDIVIDUALS RELATED TO THE TWEET USING THE CATEGORIES
			OWLObjectProperty canBeOfTheCategory = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "can_be_of_the_category"));
			for (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(twIndividuals.get(x), canBeOfTheCategory).getFlattened()) { 
	            if(ind.toStringID().contains("CD-I")) {
	            	OWLObjectProperty reportsDamagesTo = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "reports_damages_to"));
	            	ArrayList<LabelledDPVPair> cdInfo = getCategorizedTweetInfo(ind, reportsDamagesTo);
	            	CasualtiesAndDamageTweet CADT = constructCDTweet(tweetInfo, timestampInfo, locationInfo, cdInfo);
	            	retrievedCADTweets.add(CADT);
	            } else if(ind.toStringID().contains("CA-I")) {
	            	OWLObjectProperty givesOutAn = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "gives_out_an"));
	            	ArrayList<LabelledDPVPair> caInfo = getCategorizedTweetInfo(ind, givesOutAn);
	            	CautionAndAdviceTweet CAT = constructCATweet(tweetInfo, timestampInfo, locationInfo, caInfo);
	            	retrievedCATweets.add(CAT);
	            } else if(ind.toStringID().contains("D-I")) {
	            	OWLObjectProperty reportsANeedForARes = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "reports_a_need_for_a"));
	            	ArrayList<LabelledDPVPair> dInfo = getCategorizedTweetInfo(ind, reportsANeedForARes);
	            	DonationTweet DT = constructDTweet(tweetInfo, timestampInfo, locationInfo, dInfo);
	            	retrievedDTweets.add(DT);
	            } else {
	            	OWLObjectProperty reportsACallForHelp = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "reports_a_call_for_help_from_a"));
	            	ArrayList<LabelledDPVPair> cfhInfo = getCategorizedTweetInfo(ind, reportsACallForHelp);
	            	CallForHelpTweet CFHT = constructCFHTweet(tweetInfo, timestampInfo, locationInfo, cfhInfo);
	            	retrievedCFHTweets.add(CFHT);
	            }
	        }
		}
		
		RetrievedTweet retTwe = new RetrievedTweet();
		retTwe.setRetrievedCADTweets(retrievedCADTweets);
		retTwe.setRetrievedCATweets(retrievedCATweets);
		retTwe.setRetrievedCFHTweets(retrievedCFHTweets);
		retTwe.setRetrievedDTweets(retrievedDTweets);
		
		return retTwe;
	}
	
	// RETRIEVER CONSTRUCTION PRE-REQUISITE METHOD: CD Tweet Content Constructor
	public CasualtiesAndDamageTweet constructCDTweet(ArrayList<DPVPair> twInfo, ArrayList<DPVPair> tsInfo, ArrayList<DPVPair> locInfo, ArrayList<LabelledDPVPair> cdInfo) {
		CasualtiesAndDamageTweet cdt = new CasualtiesAndDamageTweet();
		
		// CONSTRUCT TWEET INFORMATION
		for(DPVPair tw : twInfo) {
			if(tw.getDataProperty().equalsIgnoreCase("tweethandle")) {
				cdt.setTweetHandle(tw.getDataValue());
			} else {
				cdt.setTweetContent(tw.getDataValue());
			}
		}
		
		// CONSTRUCT TIMESTAMP INFORMATION
		for(DPVPair ts : tsInfo) {
			if(ts.getDataProperty().equalsIgnoreCase("tweettimestamp")) {
				cdt.setTweetTimestamp(ts.getDataValue());
			} else {
				cdt.setTweetDate(ts.getDataValue());
			}
		}
		
		// CONSTRUCT LOCATION INFORMATION
		for(DPVPair loc : locInfo) {
			if(loc.getDataProperty().equalsIgnoreCase("geolocationoftweet")) {
				cdt.setTweetGeoLocation(loc.getDataValue());
			} else {
				cdt.setLocationInTweet(loc.getDataValue());
			}
		}
		
		// CONSTRUCT CD TWEET INFORMATION
		for(LabelledDPVPair cd1 : cdInfo) {
			if(cd1.getDpvLabel().equalsIgnoreCase("Victim Info")) {
				ArrayList<DPVPair> vInfo = cd1.getDpvList();
				
				// CONSTRUCT VICTIM INFORMATION
				for(DPVPair v : vInfo) {
					if(v.getDataProperty().equalsIgnoreCase("victimname")) {
						cdt.setVictimName(v.getDataValue());
					}
				}
			} else if(cd1.getDpvLabel().equalsIgnoreCase("Object Info")) {
				ArrayList<DPVPair> oInfo = cd1.getDpvList();
				
				// CONSTRUCT OBJECT INFORMATION
				for(DPVPair o : oInfo) {
					if(o.getDataProperty().equalsIgnoreCase("objectname")) {
						cdt.setObjectName(o.getDataValue());
					} else {
						cdt.setObjectDetails(o.getDataValue());
					}
				}
			}
		}
		
		return cdt;
	}
	
	// RETRIEVER CONSTRUCTION PRE-REQUISITE METHOD: CA Tweet Content Constructor
	public CautionAndAdviceTweet constructCATweet(ArrayList<DPVPair> twInfo, ArrayList<DPVPair> tsInfo, ArrayList<DPVPair> locInfo, ArrayList<LabelledDPVPair> caInfo) {
		CautionAndAdviceTweet cat = new CautionAndAdviceTweet();
		
		// CONSTRUCT TWEET INFORMATION
		for(DPVPair tw : twInfo) {
			if(tw.getDataProperty().equalsIgnoreCase("tweethandle")) {
				cat.setTweetHandle(tw.getDataValue());
			} else {
				cat.setTweetContent(tw.getDataValue());
			}
		}
		
		// CONSTRUCT TIMESTAMP INFORMATION
		for(DPVPair ts : tsInfo) {
			if(ts.getDataProperty().equalsIgnoreCase("tweettimestamp")) {
				cat.setTweetTimestamp(ts.getDataValue());
			} else {
				cat.setTweetDate(ts.getDataValue());
			}
		}
		
		// CONSTRUCT LOCATION INFORMATION
		for(DPVPair loc : locInfo) {
			if(loc.getDataProperty().equalsIgnoreCase("geolocationoftweet")) {
				cat.setTweetGeoLocation(loc.getDataValue());
			} else {
				cat.setLocationInTweet(loc.getDataValue());
			}
		}
		
		// CONSTRUCT CA TWEET INFORMATION
		for(LabelledDPVPair cd1 : caInfo) {
			if(cd1.getDpvLabel().equalsIgnoreCase("Advice Info")) {
				ArrayList<DPVPair> aInfo = cd1.getDpvList();
				
				// CONSTRUCT TWEET ADVICE INFORMATION
				for(DPVPair a : aInfo) {
					if(a.getDataProperty().equalsIgnoreCase("tweetadvice")) {
						cat.setTweetAdvice(a.getDataValue());
					}
				}
			}
		}
		
		return cat;
	}
	
	// RETRIEVER CONSTRUCTION PRE-REQUISITE METHOD: D Tweet Content Constructor
	public DonationTweet constructDTweet(ArrayList<DPVPair> twInfo, ArrayList<DPVPair> tsInfo, ArrayList<DPVPair> locInfo, ArrayList<LabelledDPVPair> dInfo) {
		DonationTweet dt = new DonationTweet();
		
		// CONSTRUCT TWEET INFORMATION
		for(DPVPair tw : twInfo) {
			if(tw.getDataProperty().equalsIgnoreCase("tweethandle")) {
				dt.setTweetHandle(tw.getDataValue());
			} else {
				dt.setTweetContent(tw.getDataValue());
			}
		}
		
		// CONSTRUCT TIMESTAMP INFORMATION
		for(DPVPair ts : tsInfo) {
			if(ts.getDataProperty().equalsIgnoreCase("tweettimestamp")) {
				dt.setTweetTimestamp(ts.getDataValue());
			} else {
				dt.setTweetDate(ts.getDataValue());
			}
		}
		
		// CONSTRUCT LOCATION INFORMATION
		for(DPVPair loc : locInfo) {
			if(loc.getDataProperty().equalsIgnoreCase("geolocationoftweet")) {
				dt.setTweetGeoLocation(loc.getDataValue());
			} else {
				dt.setLocationInTweet(loc.getDataValue());
			}
		}
		
		// CONSTRUCT D TWEET INFORMATION
		for(LabelledDPVPair d1 : dInfo) {
			if(d1.getDpvLabel().equalsIgnoreCase("Victim Info")) {
				ArrayList<DPVPair> vInfo = d1.getDpvList();
				
				// CONSTRUCT VICTIM INFORMATION
				for(DPVPair v : vInfo) {
					if(v.getDataProperty().equalsIgnoreCase("victimname")) {
						dt.setVictimName(v.getDataValue());
					}
				}
			} else if(d1.getDpvLabel().equalsIgnoreCase("Resource Info")) {
				ArrayList<DPVPair> rInfo = d1.getDpvList();
				
				// CONSTRUCT OBJECT INFORMATION
				for(DPVPair r : rInfo) {
					if(r.getDataProperty().equalsIgnoreCase("resourcename")) {
						dt.setResourceName(r.getDataValue());
					} else {
						dt.setResourceDetails(r.getDataValue());
					}
				}
			}
		}
		
		return dt;
	}
	
	// RETRIEVER CONSTRUCTION PRE-REQUISITE METHOD: CFH Tweet Content Constructor
	public CallForHelpTweet constructCFHTweet(ArrayList<DPVPair> twInfo, ArrayList<DPVPair> tsInfo, ArrayList<DPVPair> locInfo, ArrayList<LabelledDPVPair> cfhInfo) {
		CallForHelpTweet cfht = new CallForHelpTweet();
		
		// CONSTRUCT TWEET INFORMATION
		for(DPVPair tw : twInfo) {
			if(tw.getDataProperty().equalsIgnoreCase("tweethandle")) {
				cfht.setTweetHandle(tw.getDataValue());
			} else {
				cfht.setTweetContent(tw.getDataValue());
			}
		}
		
		// CONSTRUCT TIMESTAMP INFORMATION
		for(DPVPair ts : tsInfo) {
			if(ts.getDataProperty().equalsIgnoreCase("tweettimestamp")) {
				cfht.setTweetTimestamp(ts.getDataValue());
			} else {
				cfht.setTweetDate(ts.getDataValue());
			}
		}
		
		// CONSTRUCT LOCATION INFORMATION
		for(DPVPair loc : locInfo) {
			if(loc.getDataProperty().equalsIgnoreCase("geolocationoftweet")) {
				cfht.setTweetGeoLocation(loc.getDataValue());
			} else {
				cfht.setLocationInTweet(loc.getDataValue());
			}
		}
		
		// CONSTRUCT D TWEET INFORMATION
		for(LabelledDPVPair cfh1 : cfhInfo) {
			if(cfh1.getDpvLabel().equalsIgnoreCase("Victim Info")) {
				ArrayList<DPVPair> vInfo = cfh1.getDpvList();
				
				// CONSTRUCT VICTIM INFORMATION
				for(DPVPair v : vInfo) {
					if(v.getDataProperty().equalsIgnoreCase("victimname")) {
						cfht.setVictimName(v.getDataValue());
					}
				}
			}
		}
		
		return cfht;
	}
	
	// INSTANCE RETRIEVER PRE-REQUISITE METHOD: Instance Categorized Information Retriever
	public ArrayList<LabelledDPVPair> getCategorizedTweetInfo(OWLNamedIndividual tweetIndividual, OWLObjectProperty theProperty) {
		// PREPARE THE REASONER AND PREREQUISITE STUFF
		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createReasoner(filietOntology);
		
		ArrayList<LabelledDPVPair> catTweetInfo = new ArrayList<LabelledDPVPair>();
		
		// DISPLAY THE ADVICE INSTANCE RELATED TO THE CA INSTANCE
		for (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(tweetIndividual, theProperty).getFlattened()) { 
            if(ind.toStringID().contains("VIC-I")) {
            	ArrayList<DPVPair> victimInfo = getDataPropertyValues(ind);
            	LabelledDPVPair ldp = new LabelledDPVPair();
            	ldp.setDpvLabel("Victim Info");
            	ldp.setDpvList(victimInfo);
            	catTweetInfo.add(ldp);
            } else if(ind.toStringID().contains("OBJ-I")) {
            	ArrayList<DPVPair> objectInfo = getDataPropertyValues(ind);
            	LabelledDPVPair ldp = new LabelledDPVPair();
            	ldp.setDpvLabel("Object Info");
            	ldp.setDpvList(objectInfo);
            	catTweetInfo.add(ldp);
            } else if(ind.toStringID().contains("A-I")) {
            	ArrayList<DPVPair> adviceInfo = getDataPropertyValues(ind);
            	LabelledDPVPair ldp = new LabelledDPVPair();
            	ldp.setDpvLabel("Advice Info");
            	ldp.setDpvList(adviceInfo);
            	catTweetInfo.add(ldp);
            } else {
            	ArrayList<DPVPair> resourceInfo = getDataPropertyValues(ind);
            	LabelledDPVPair ldp = new LabelledDPVPair();
            	ldp.setDpvLabel("Resource Info");
            	ldp.setDpvList(resourceInfo);
            	catTweetInfo.add(ldp);
            }
        }
		
		return catTweetInfo;
	}
	
	// INSTANCE RETRIEVER PRE-REQUISITE METHOD: Instance Data Property Value Retriever
	public ArrayList<DPVPair> getDataPropertyValues(OWLNamedIndividual individual) {
		// INITIALIZE NEEDED VARIABLES FOR THIS OPERATION
		ArrayList<DPVPair> list = new ArrayList<DPVPair>();
		Map<OWLDataPropertyExpression, Set<OWLLiteral>> data = individual.getDataPropertyValues(filietOntology);
		Iterator<Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>>> iterator1 = data.entrySet().iterator();

		while (iterator1.hasNext()) {
			DPVPair dpvp = new DPVPair();
			Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> dVal = iterator1.next();

			// STORE THE DATA PROPERTY IN THE DATA PROPERTY VARIABLE
			String s = dVal.getKey().toString();
			dpvp.setDataProperty(s.substring(86, (s.length() - 1)));
			
			// ITERATE THROUGH THE DATA VALUES AVAILABLE FOR THE SAID PROPERTY
			Set<OWLLiteral> dV = dVal.getValue();
			ArrayList<OWLLiteral> dataVal = new ArrayList<OWLLiteral>(dV);

			for (int c = 0; c < dataVal.size(); c++) {
				// STORE THE DATA VALUE IN THE DATA VALUE VARIABLE
				dpvp.setDataValue(dataVal.get(c).getLiteral());
			}
			
			// ADD THE DPVP INSTANCE TO THE ARRAYLIST
			list.add(dpvp);
		}
		
		return list;
	}
}
