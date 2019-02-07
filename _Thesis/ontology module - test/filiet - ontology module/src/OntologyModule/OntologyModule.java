package OntologyModule;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
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
import Tweet.Tweet;

public class OntologyModule {
	OWLOntologyManager manager;
	OWLOntology filietOntology;
	File file;

	private static String BASE_IRI = "http://www.semanticweb.org/kylemchalebdelacruz/ontologies/2014/9/untitled-ontology-5#";
	private static String ADVICE_CLASS_IRI = BASE_IRI + "Advice";
	private static String CASUALTIES_DAMAGE_CLASS_IRI = BASE_IRI + "CasualtiesAndDamage";
	private static String CAUTION_ADVICE_CLASS_IRI = BASE_IRI + "CautionAndAdvice";
	private static String DONATION_CLASS_IRI = BASE_IRI + "Donation";
	private static String LOCATION_CLASS_IRI = BASE_IRI + "Location";
	private static String OBJECT_CLASS_IRI = BASE_IRI + "Object";
	private static String RESOURCE_CLASS_IRI = BASE_IRI + "Resource";
	private static String TIMESTAMP_CLASS_IRI = BASE_IRI + "Timestamp";
	private static String TWEET_CLASS_IRI = BASE_IRI + "Tweet";
	private static String VICTIM_CLASS_IRI = BASE_IRI + "Victim";
	private static String CALL_FOR_HELP_CLASS_IRI = BASE_IRI + "CallForHelp";

	public OntologyModule() {
		// Constructor
	}
	
	// ONTOLOGY PRE-REQUISITE METHOD: Ontology Loader
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
	
	// ONTOLOGY PRE-REQUISITE METHOD: Ontology Saver
	public void saveFilietOntology() throws OWLOntologyStorageException {
		manager.saveOntology(filietOntology);
	}

	// ONTOLOGY PRE-REQUISITE METHOD: Ontology Remover
	public void removeOntologyFromManager() {
		manager.removeOntology(filietOntology);
	}
	
	// ONTOLOGY VIEWER PRE-REQUISITE METHOD: Main Viewer
	public void displayStoredTweets() {
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
		
		// LIST DOWN THE INSTANCES OF THE TWEET CLASS
		System.out.println("\n<+> STORED TWEETS IN THE FILIET ONTOLOGY:");
		if(twIndividuals.isEmpty())
			System.out.println("    > NO TWEETS ARE STORED IN THIS ONTOLOGY!");
		else {
			for(int x = 0; x < twIndividuals.size(); x++) {
				System.out.println("    > FILIET-ONT-TWT-" + (x + 1) + ": " /*+ twIndividuals.get(x).toStringID().substring(85)*/);
				System.out.println("      >> Tweet Information:");
				
				// DISPLAY THE DATA PROPERTIES OF THE TWEET INSTANCE
				printDataPropertyValues(twIndividuals.get(x));
				
				// DISPLAY THE LOCATION AND TIMESTAMP INFO OF THE TWEET INSTANCE
				OWLObjectProperty hasThisInfo = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "has_this_information"));
				for (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(twIndividuals.get(x), hasThisInfo).getFlattened()) { 
		            System.out.println("      >> OTHER INFORMATION -> [" + ind.toStringID().substring(85) + "]");
		            printDataPropertyValues(ind);
		        }
				
				// DISPLAY THE LOCATION AND TIMESTAMP INFO OF THE TWEET INSTANCE
				OWLObjectProperty canBeOfTheCategory = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "can_be_of_the_category"));
				for (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(twIndividuals.get(x), canBeOfTheCategory).getFlattened()) { 
		            System.out.print("      >> CATEGORY -> ");
		            
		            if(ind.toStringID().contains("CD-I")) {
		            	System.out.println("CASUALTIES AND DAMAGE [" + ind.toStringID().substring(85) + "] - reports damages to:");
		            	OWLObjectProperty reportsDamagesTo = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "reports_damages_to"));
		            	printCategorizedTweetInfo(ind, reportsDamagesTo);
		            } else if(ind.toStringID().contains("CA-I")) {
		            	System.out.println("CAUTION AND ADVICE [" + ind.toStringID().substring(85) + "] - gives out an:");
		            	OWLObjectProperty givesOutAn = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "gives_out_an"));
		            	printCategorizedTweetInfo(ind, givesOutAn);
		            } else if(ind.toStringID().contains("D-I")) {
		            	System.out.println("DONATION [" + ind.toStringID().substring(85) + "] - reports a need for a:");
		            	OWLObjectProperty reportsANeedForARes = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "reports_a_need_for_a"));
		            	printCategorizedTweetInfo(ind, reportsANeedForARes);
		            } else {
		            	System.out.println("CALL FOR HELP [" + ind.toStringID().substring(85) + "] - reports a call for help from a:");
		            	OWLObjectProperty reportsACallForHelp = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "reports_a_call_for_help_from_a"));
		            	printCategorizedTweetInfo(ind, reportsACallForHelp);
		            }
		        }
				
				System.out.println("");
			}
		}
	}
	
	// ONTOLOGY VIEWER PRE-REQUISITE METHOD: Categorized Tweet Information Viewer
	public void printCategorizedTweetInfo(OWLNamedIndividual theIndividual, OWLObjectProperty theProperty) {
		// PREPARE THE REASONER AND PREREQUISITE STUFF
		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createReasoner(filietOntology);
		
		// DISPLAY THE ADVICE INSTANCE RELATED TO THE CA INSTANCE
		for (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(theIndividual, theProperty).getFlattened()) { 
            System.out.println("         [" + ind.toStringID().substring(85) + "]");
            printDataPropertyValues(ind);
        }
	}
	
	// ONTOLOGY VIEWER PRE-REQUISITE METHOD: Internal Data Property Viewer
	public void printDataPropertyValues(OWLNamedIndividual individual) {
		Map<OWLDataPropertyExpression, Set<OWLLiteral>> data = individual.getDataPropertyValues(filietOntology);
		Iterator<Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>>> iterator1 = data.entrySet().iterator();

		while (iterator1.hasNext()) {
			Map.Entry<OWLDataPropertyExpression, Set<OWLLiteral>> dVal = iterator1.next();

			String s = dVal.getKey().toString();
			//System.out.print("       + |DP| : " + s.substring(86, (s.length() - 1)) + " :");
			System.out.print("         + " + s.substring(86, (s.length() - 1)) + " :");

			Set<OWLLiteral> dV = dVal.getValue();
			ArrayList<OWLLiteral> dataVal = new ArrayList<OWLLiteral>(dV);

			for (int c = 0; c < dataVal.size(); c++) {
				System.out.println(" (" + dataVal.get(c).getLiteral() + ")");
			}
		}
	}
	
	// ONTOLOGY WAREHOUSING PRE-REQUISITE METHOD: Tweet Information
	public OWLIndividual addTweetInformation(Tweet t) {
		// PREPARE THE REASONER AND PREREQUISITE STUFF
		OWLDataFactory dataFactory = manager.getOWLDataFactory();
						
		// INSTANTIATE CLASSES NEEDED FOR THIS OPERATION
		OWLClass tweetClass = dataFactory.getOWLClass(IRI.create(TWEET_CLASS_IRI));
		OWLClass locationClass = dataFactory.getOWLClass(IRI.create(LOCATION_CLASS_IRI));
		OWLClass timestampClass = dataFactory.getOWLClass(IRI.create(TIMESTAMP_CLASS_IRI));
				
		// CREATE THE NEEDED INDIVIDUALS FOR THIS OPERATION
		OWLIndividual tweetIndividual = dataFactory.getOWLNamedIndividual(IRI.create(BASE_IRI + "_TI_" + t.getTweetContent().replace(" ", "_")));
		OWLIndividual locationIndividual = dataFactory.getOWLNamedIndividual(IRI.create(BASE_IRI + "_LI_" + t.getTweetContent().replace(" ", "_")));
		OWLIndividual timestampIndividual = dataFactory.getOWLNamedIndividual(IRI.create(BASE_IRI + "_TSI_" + t.getTweetContent().replace(" ", "_")));
				
		// SAY THAT THE INDIVIDUALS ARE INSTANCES OF THE CLASSES
		Set<OWLClassAssertionAxiom> classAssertion = new HashSet<OWLClassAssertionAxiom>();
						
		OWLClassAssertionAxiom tAssertion = dataFactory.getOWLClassAssertionAxiom(tweetClass, tweetIndividual); 
			classAssertion.add(tAssertion);
		OWLClassAssertionAxiom lAssertion = dataFactory.getOWLClassAssertionAxiom(locationClass, locationIndividual);
			classAssertion.add(lAssertion);
		OWLClassAssertionAxiom tsAssertion = dataFactory.getOWLClassAssertionAxiom(timestampClass, timestampIndividual);
			classAssertion.add(tsAssertion);
				
		manager.addAxioms(filietOntology, classAssertion);
				
		// ADD THE DATA PROPERTIES TO THE INDIVIDUALS
		Set<OWLDataPropertyAssertionAxiom> dataAssertion = new HashSet<OWLDataPropertyAssertionAxiom>();
						
			// These are the data properties for the Tweet Individual
			OWLDataProperty tweetHandle = dataFactory.getOWLDataProperty(IRI.create(BASE_IRI + "tweetHandle"));
			OWLDataPropertyAssertionAxiom tweetHandleDataPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(tweetHandle, tweetIndividual, t.getTweetHandle());
				dataAssertion.add(tweetHandleDataPropertyAssertion);
			OWLDataProperty tweetContent = dataFactory.getOWLDataProperty(IRI.create(BASE_IRI + "tweetContent"));
			OWLDataPropertyAssertionAxiom tweetContentDataPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(tweetContent, tweetIndividual, t.getTweetContent());
				dataAssertion.add(tweetContentDataPropertyAssertion);
					
			// These are the data properties for the Location Individual
			OWLDataProperty geoLocationOfTweet = dataFactory.getOWLDataProperty(IRI.create(BASE_IRI + "geoLocationOfTweet"));
			OWLDataPropertyAssertionAxiom geoLocationOfTweetDataPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(geoLocationOfTweet, locationIndividual, t.getTweetGeoLocation());
				dataAssertion.add(geoLocationOfTweetDataPropertyAssertion);
			OWLDataProperty locationInTweet = dataFactory.getOWLDataProperty(IRI.create(BASE_IRI + "locationInTweet"));
			OWLDataPropertyAssertionAxiom locationInTweetDataPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(locationInTweet, locationIndividual, t.getLocationInTweet());
				dataAssertion.add(locationInTweetDataPropertyAssertion);
				
			// These are the data properties for the Timestamp Individual
			OWLDataProperty tweetTimestamp = dataFactory.getOWLDataProperty(IRI.create(BASE_IRI + "tweetTimestamp"));
			OWLDataPropertyAssertionAxiom tweetTimestampDataPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(tweetTimestamp, timestampIndividual, t.getTweetTimestamp());
				dataAssertion.add(tweetTimestampDataPropertyAssertion);
			OWLDataProperty tweetDate = dataFactory.getOWLDataProperty(IRI.create(BASE_IRI + "tweetDate"));
			OWLDataPropertyAssertionAxiom tweetDateDataPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(tweetDate, timestampIndividual, t.getTweetDate());
				dataAssertion.add(tweetDateDataPropertyAssertion);
		        
		manager.addAxioms(filietOntology, dataAssertion);
				
		// CREATE THE OBJECT PROPERTIES OF THE CLASSES INVOLVED AND ASSERT THEM
        Set<OWLObjectPropertyAssertionAxiom> objectAssertion = new HashSet<OWLObjectPropertyAssertionAxiom>();
		        
        OWLObjectProperty hasThisInfo = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "has_this_information"));
        OWLObjectPropertyAssertionAxiom hasLocationAssertion = dataFactory.getOWLObjectPropertyAssertionAxiom(hasThisInfo, tweetIndividual, locationIndividual);
        	objectAssertion.add(hasLocationAssertion);
        OWLObjectPropertyAssertionAxiom hasTimestampAssertion = dataFactory.getOWLObjectPropertyAssertionAxiom(hasThisInfo, tweetIndividual, timestampIndividual);
			objectAssertion.add(hasTimestampAssertion);
				
		manager.addAxioms(filietOntology, objectAssertion);
				
		// SAVE CHANGES TO ONTOLOGY
		try {
			saveFilietOntology();
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		}
		
		return tweetIndividual;
	}
	
	// ONTOLOGY WAREHOUSING PRE-REQUISITE METHOD: Casualties and Damage Report Information
	public void addCasualtiesAndDamageReport(CasualtiesAndDamageTweet cd) {
		// PREPARE THE TWEET INDIVIDUAL
		OWLIndividual tweetIndividual = addTweetInformation(cd);
		
		// PREPARE THE REASONER AND PREREQUISITE STUFF
		OWLDataFactory dataFactory = manager.getOWLDataFactory();
				
		// INSTANTIATE CLASSES NEEDED FOR THIS OPERATION
		OWLClass casualtiesDamageClass = dataFactory.getOWLClass(IRI.create(CASUALTIES_DAMAGE_CLASS_IRI));
		OWLClass objectClass = dataFactory.getOWLClass(IRI.create(OBJECT_CLASS_IRI));
		OWLClass victimClass = dataFactory.getOWLClass(IRI.create(VICTIM_CLASS_IRI));
		
		// CREATE THE NEEDED INDIVIDUALS FOR THIS OPERATION
		OWLIndividual cdIndividual = dataFactory.getOWLNamedIndividual(IRI.create(BASE_IRI + "_CD-I_" + cd.getObjectName().replace(" ", "_")));
		OWLIndividual objIndividual = dataFactory.getOWLNamedIndividual(IRI.create(BASE_IRI + "_OBJ-I_" + cd.getObjectName().replace(" ", "_")));
		OWLIndividual vicIndividual = dataFactory.getOWLNamedIndividual(IRI.create(BASE_IRI + "_VIC-I_" + cd.getObjectName().replace(" ", "_")));
		
		// SAY THAT THE INDIVIDUALS ARE INSTANCES OF THE CLASSES
		Set<OWLClassAssertionAxiom> classAssertion = new HashSet<OWLClassAssertionAxiom>();
				
		OWLClassAssertionAxiom cdAssertion = dataFactory.getOWLClassAssertionAxiom(casualtiesDamageClass, cdIndividual); 
			classAssertion.add(cdAssertion);
		OWLClassAssertionAxiom objAssertion = dataFactory.getOWLClassAssertionAxiom(objectClass, objIndividual);
			classAssertion.add(objAssertion);
		OWLClassAssertionAxiom vicAssertion = dataFactory.getOWLClassAssertionAxiom(victimClass, vicIndividual);
			classAssertion.add(vicAssertion);
				
		manager.addAxioms(filietOntology, classAssertion);
		
		// ADD THE DATA PROPERTIES TO THE INDIVIDUALS
		Set<OWLDataPropertyAssertionAxiom> dataAssertion = new HashSet<OWLDataPropertyAssertionAxiom>();
						
			// These are the data properties for the Object Individual
			OWLDataProperty objectName = dataFactory.getOWLDataProperty(IRI.create(BASE_IRI + "objectName"));
			OWLDataPropertyAssertionAxiom objectNameDataPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(objectName, objIndividual, cd.getObjectName());
				dataAssertion.add(objectNameDataPropertyAssertion);
			OWLDataProperty objectDetails = dataFactory.getOWLDataProperty(IRI.create(BASE_IRI + "objectDetails"));
			OWLDataPropertyAssertionAxiom objectDetailsDataPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(objectDetails, objIndividual, cd.getObjectDetails());
				dataAssertion.add(objectDetailsDataPropertyAssertion);
					
			// These are the data properties for the Victim Individual
			OWLDataProperty victimName = dataFactory.getOWLDataProperty(IRI.create(BASE_IRI + "victimName"));
			OWLDataPropertyAssertionAxiom geoLocationOfTweetDataPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(victimName, vicIndividual, cd.getVictimName());
				dataAssertion.add(geoLocationOfTweetDataPropertyAssertion);
							        
		manager.addAxioms(filietOntology, dataAssertion);
		
		// CREATE THE OBJECT PROPERTIES OF THE CLASSES INVOLVED AND ASSERT THEM
        Set<OWLObjectPropertyAssertionAxiom> objectAssertion = new HashSet<OWLObjectPropertyAssertionAxiom>();
        
        // Say that the Tweet Individual is of the category CA Individual
        OWLObjectProperty canBeOfTheCategory = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "can_be_of_the_category"));
		OWLObjectPropertyAssertionAxiom canBeAssertion = dataFactory.getOWLObjectPropertyAssertionAxiom(canBeOfTheCategory, tweetIndividual, cdIndividual);
			objectAssertion.add(canBeAssertion);
		
		OWLObjectProperty reportsDamagesTo = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "reports_damages_to"));
		OWLObjectPropertyAssertionAxiom reportsObjAssertion = dataFactory.getOWLObjectPropertyAssertionAxiom(reportsDamagesTo, cdIndividual, objIndividual);
			objectAssertion.add(reportsObjAssertion);
		OWLObjectPropertyAssertionAxiom reportsVicAssertion = dataFactory.getOWLObjectPropertyAssertionAxiom(reportsDamagesTo, cdIndividual, vicIndividual);
			objectAssertion.add(reportsVicAssertion);
		
		manager.addAxioms(filietOntology, objectAssertion);
		
		// SAVE CHANGES TO ONTOLOGY
		try {
			saveFilietOntology();
			System.out.println("<+> CASUALTIES AND DAMAGE REPORT ADDED!");
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		}
	}
	
	// ONTOLOGY WAREHOUSING PRE-REQUISITE METHOD: Caution and Advice Report Information
	public void addCautionAndAdviceReport(CautionAndAdviceTweet ca) {
		// PREPARE THE TWEET INDIVIDUAL
		OWLIndividual tweetIndividual = addTweetInformation(ca);
		
		// PREPARE THE REASONER AND PREREQUISITE STUFF
		OWLDataFactory dataFactory = manager.getOWLDataFactory();
		
		// INSTANTIATE CLASSES NEEDED FOR THIS OPERATION
		OWLClass cautionAdviceClass = dataFactory.getOWLClass(IRI.create(CAUTION_ADVICE_CLASS_IRI));
		OWLClass adviceClass = dataFactory.getOWLClass(IRI.create(ADVICE_CLASS_IRI));
		
		// CREATE THE NEEDED INDIVIDUALS FOR THIS OPERATION
		OWLIndividual caIndividual = dataFactory.getOWLNamedIndividual(IRI.create(BASE_IRI + "_CA-I_" + ca.getTweetAdvice().replace(" ", "_")));
		OWLIndividual adviceIndividual = dataFactory.getOWLNamedIndividual(IRI.create(BASE_IRI + "_A-I_" + ca.getTweetAdvice().replace(" ", "_")));
		
		// SAY THAT THE INDIVIDUALS ARE INSTANCES OF THE CLASSES
		Set<OWLClassAssertionAxiom> classAssertion = new HashSet<OWLClassAssertionAxiom>();
		
		OWLClassAssertionAxiom caAssertion = dataFactory.getOWLClassAssertionAxiom(cautionAdviceClass, caIndividual); 
			classAssertion.add(caAssertion);
		OWLClassAssertionAxiom adAssertion = dataFactory.getOWLClassAssertionAxiom(adviceClass, adviceIndividual);
			classAssertion.add(adAssertion);
		
		manager.addAxioms(filietOntology, classAssertion);
		
		// ADD THE DATA PROPERTIES TO THE INDIVIDUALS
		Set<OWLDataPropertyAssertionAxiom> dataAssertion = new HashSet<OWLDataPropertyAssertionAxiom>();
		
		OWLDataProperty tweetAdvice = dataFactory.getOWLDataProperty(IRI.create(BASE_IRI + "tweetAdvice"));
        OWLDataPropertyAssertionAxiom adviceDataPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(tweetAdvice, adviceIndividual, ca.getTweetAdvice());
        	dataAssertion.add(adviceDataPropertyAssertion);
        
        manager.addAxioms(filietOntology, dataAssertion);
		
		// CREATE THE OBJECT PROPERTIES OF THE CLASSES INVOLVED AND ASSERT THEM
        Set<OWLObjectPropertyAssertionAxiom> objectAssertion = new HashSet<OWLObjectPropertyAssertionAxiom>();
        
        // Say that the Tweet Individual is of the category CA Individual
        OWLObjectProperty canBeOfTheCategory = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "can_be_of_the_category"));
		OWLObjectPropertyAssertionAxiom canBeAssertion = dataFactory.getOWLObjectPropertyAssertionAxiom(canBeOfTheCategory, tweetIndividual, caIndividual);
			objectAssertion.add(canBeAssertion);
		
		OWLObjectProperty givesOutAn = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "gives_out_an"));
		OWLObjectPropertyAssertionAxiom givesAssertion = dataFactory.getOWLObjectPropertyAssertionAxiom(givesOutAn, caIndividual, adviceIndividual);
			objectAssertion.add(givesAssertion);
		
		manager.addAxioms(filietOntology, objectAssertion);
		
		// SAVE CHANGES TO THE ONTOLOGY
		try {
			saveFilietOntology();
			System.out.println("<+> CAUTION AND ADVICE REPORT ADDED!");
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		}
	}
	
	// ONTOLOGY WAREHOUSING PRE-REQUISITE METHOD: Donation Report Information
	public void addDonationReport(DonationTweet d) {
		// PREPARE THE TWEET INDIVIDUAL
		OWLIndividual tweetIndividual = addTweetInformation(d);
		
		// PREPARE THE REASONER AND PREREQUISITE STUFF
		OWLDataFactory dataFactory = manager.getOWLDataFactory();
						
		// INSTANTIATE CLASSES NEEDED FOR THIS OPERATION
		OWLClass donationClass = dataFactory.getOWLClass(IRI.create(DONATION_CLASS_IRI));
		OWLClass victimClass = dataFactory.getOWLClass(IRI.create(VICTIM_CLASS_IRI));
		OWLClass resourceClass = dataFactory.getOWLClass(IRI.create(RESOURCE_CLASS_IRI));
		
		// CREATE THE NEEDED INDIVIDUALS FOR THIS OPERATION
		OWLIndividual dIndividual = dataFactory.getOWLNamedIndividual(IRI.create(BASE_IRI + "_D-I_" + d.getVictimName().replace(" ", "_")));
		OWLIndividual vicIndividual = dataFactory.getOWLNamedIndividual(IRI.create(BASE_IRI + "_VIC-I_" + d.getVictimName().replace(" ", "_")));
		OWLIndividual resIndividual = dataFactory.getOWLNamedIndividual(IRI.create(BASE_IRI + "_RES-I_" + d.getVictimName().replace(" ", "_")));
				
		// SAY THAT THE INDIVIDUALS ARE INSTANCES OF THE CLASSES
		Set<OWLClassAssertionAxiom> classAssertion = new HashSet<OWLClassAssertionAxiom>();
						
		OWLClassAssertionAxiom cdAssertion = dataFactory.getOWLClassAssertionAxiom(donationClass, dIndividual); 
			classAssertion.add(cdAssertion);
		OWLClassAssertionAxiom vicAssertion = dataFactory.getOWLClassAssertionAxiom(victimClass, vicIndividual);
			classAssertion.add(vicAssertion);
		OWLClassAssertionAxiom resAssertion = dataFactory.getOWLClassAssertionAxiom(resourceClass, resIndividual);
			classAssertion.add(resAssertion);
						
		manager.addAxioms(filietOntology, classAssertion);
		
		// ADD THE DATA PROPERTIES TO THE INDIVIDUALS
		Set<OWLDataPropertyAssertionAxiom> dataAssertion = new HashSet<OWLDataPropertyAssertionAxiom>();
								
			// These are the data properties for the Victim Individual
			OWLDataProperty victimName = dataFactory.getOWLDataProperty(IRI.create(BASE_IRI + "victimName"));
			OWLDataPropertyAssertionAxiom geoLocationOfTweetDataPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(victimName, vicIndividual, d.getVictimName());
				dataAssertion.add(geoLocationOfTweetDataPropertyAssertion);
				
			// These are the data properties for the Resource Individual
			OWLDataProperty resourceName = dataFactory.getOWLDataProperty(IRI.create(BASE_IRI + "resourceName"));
			OWLDataPropertyAssertionAxiom resourceNameDataPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(resourceName, resIndividual, d.getResourceName());
				dataAssertion.add(resourceNameDataPropertyAssertion);
			OWLDataProperty resourceDetails = dataFactory.getOWLDataProperty(IRI.create(BASE_IRI + "resourceDetails"));
			OWLDataPropertyAssertionAxiom resourceDetailsDataPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(resourceDetails, resIndividual, d.getResourceDetails());
				dataAssertion.add(resourceDetailsDataPropertyAssertion);
									        
		manager.addAxioms(filietOntology, dataAssertion);
		
		// CREATE THE OBJECT PROPERTIES OF THE CLASSES INVOLVED AND ASSERT THEM
        Set<OWLObjectPropertyAssertionAxiom> objectAssertion = new HashSet<OWLObjectPropertyAssertionAxiom>();
        
        // Say that the Tweet Individual is of the category D Individual
        OWLObjectProperty canBeOfTheCategory = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "can_be_of_the_category"));
		OWLObjectPropertyAssertionAxiom canBeAssertion = dataFactory.getOWLObjectPropertyAssertionAxiom(canBeOfTheCategory, tweetIndividual, dIndividual);
			objectAssertion.add(canBeAssertion);
		
		OWLObjectProperty reportsANeedForARes = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "reports_a_need_for_a"));
		OWLObjectPropertyAssertionAxiom reportsANeedForAResAssertion = dataFactory.getOWLObjectPropertyAssertionAxiom(reportsANeedForARes, dIndividual, resIndividual);
			objectAssertion.add(reportsANeedForAResAssertion);
		
		OWLObjectProperty fromAVic = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "from_a"));
		OWLObjectPropertyAssertionAxiom fromAVicAssertion = dataFactory.getOWLObjectPropertyAssertionAxiom(fromAVic, resIndividual, vicIndividual);
			objectAssertion.add(fromAVicAssertion);
			
		manager.addAxioms(filietOntology, objectAssertion);
		
		// SAVE CHANGES TO ONTOLOGY
		try {
			saveFilietOntology();
			System.out.println("<+> DONATION REPORT ADDED!");
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		}
	}
	
	// ONTOLOGY WAREHOUSING PRE-REQUISITE METHOD: Call For Help Report Information
	public void addCallForHelpReport(CallForHelpTweet cfh) {
		// PREPARE THE TWEET INDIVIDUAL
		OWLIndividual tweetIndividual = addTweetInformation(cfh);
		
		// PREPARE THE REASONER AND PREREQUISITE STUFF
		OWLDataFactory dataFactory = manager.getOWLDataFactory();
						
		// INSTANTIATE CLASSES NEEDED FOR THIS OPERATION
		OWLClass callForHelpClass = dataFactory.getOWLClass(IRI.create(CALL_FOR_HELP_CLASS_IRI));
		OWLClass victimClass = dataFactory.getOWLClass(IRI.create(VICTIM_CLASS_IRI));
			
		// CREATE THE NEEDED INDIVIDUALS FOR THIS OPERATION
		OWLIndividual cfhIndividual = dataFactory.getOWLNamedIndividual(IRI.create(BASE_IRI + "_CFH-I_" + cfh.getVictimName().replace(" ", "_")));
		OWLIndividual vicIndividual = dataFactory.getOWLNamedIndividual(IRI.create(BASE_IRI + "_VIC-I_" + cfh.getVictimName().replace(" ", "_")));
					
		// SAY THAT THE INDIVIDUALS ARE INSTANCES OF THE CLASSES
		Set<OWLClassAssertionAxiom> classAssertion = new HashSet<OWLClassAssertionAxiom>();
							
		OWLClassAssertionAxiom cfhAssertion = dataFactory.getOWLClassAssertionAxiom(callForHelpClass, cfhIndividual); 
			classAssertion.add(cfhAssertion);
		OWLClassAssertionAxiom vicAssertion = dataFactory.getOWLClassAssertionAxiom(victimClass, vicIndividual);
			classAssertion.add(vicAssertion);
							
		manager.addAxioms(filietOntology, classAssertion);
			
		// ADD THE DATA PROPERTIES TO THE INDIVIDUALS
		Set<OWLDataPropertyAssertionAxiom> dataAssertion = new HashSet<OWLDataPropertyAssertionAxiom>();
									
			// These are the data properties for the Victim Individual
			OWLDataProperty victimName = dataFactory.getOWLDataProperty(IRI.create(BASE_IRI + "victimName"));
			OWLDataPropertyAssertionAxiom geoLocationOfTweetDataPropertyAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(victimName, vicIndividual, cfh.getVictimName());
				dataAssertion.add(geoLocationOfTweetDataPropertyAssertion);
										        
		manager.addAxioms(filietOntology, dataAssertion);
			
		// CREATE THE OBJECT PROPERTIES OF THE CLASSES INVOLVED AND ASSERT THEM
	    Set<OWLObjectPropertyAssertionAxiom> objectAssertion = new HashSet<OWLObjectPropertyAssertionAxiom>();
	        
	    // Say that the Tweet Individual is of the category CA Individual
        OWLObjectProperty canBeOfTheCategory = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "can_be_of_the_category"));
		OWLObjectPropertyAssertionAxiom canBeAssertion = dataFactory.getOWLObjectPropertyAssertionAxiom(canBeOfTheCategory, tweetIndividual, cfhIndividual);
			objectAssertion.add(canBeAssertion);
			
		OWLObjectProperty reportsACallForHelp = dataFactory.getOWLObjectProperty(IRI.create(BASE_IRI + "reports_a_call_for_help_from_a"));
		OWLObjectPropertyAssertionAxiom reportsACallVicAssertion = dataFactory.getOWLObjectPropertyAssertionAxiom(reportsACallForHelp, cfhIndividual, vicIndividual);
			objectAssertion.add(reportsACallVicAssertion);
			
		manager.addAxioms(filietOntology, objectAssertion);
			
		// SAVE CHANGES TO ONTOLOGY
		try {
			saveFilietOntology();
			System.out.println("<+> CALL FOR HELP REPORT ADDED!");
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		}
	}
}
