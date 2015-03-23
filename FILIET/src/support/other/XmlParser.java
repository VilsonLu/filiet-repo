package support.other;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import ontology.model.CallForHelpTweet;
import ontology.model.CasualtiesAndDamageTweet;
import ontology.model.CautionAndAdviceTweet;
import ontology.model.DonationTweet;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import binder.Binder;
import support.model.Sentence;
import support.model.Tweet;

public class XmlParser {

	public static void saveXMLCD(List<Sentence> sentences, String outputPath)
			throws FileNotFoundException, XMLStreamException {
		try {
			Element dataset = new Element("dataset");
			dataset.setAttribute(new Attribute("name", "ruby-dataset"));
			Document doc = new Document(dataset);
			doc.setRootElement(dataset);
			for (Sentence s : sentences) {
				Tweet t = s.getTweets();

				Element tweet = new Element("tweet");
				tweet.setAttribute(new Attribute("id", Long.toString(t
						.getTweetID())));
				tweet.addContent(new Element("user").setText(t.getUser()));
				tweet.addContent(new Element("tweet").setText(s.getRawTweet()));

				tweet.addContent(new Element("prediction").setText(s
						.getCategory()));
				tweet.addContent(new Element("actual").setText(t.getCategory()));

				Element extracted = new Element("extracted");

				if (t.getCategory().equalsIgnoreCase("CD")) {
					CasualtiesAndDamageTweet cd = Binder.bindCD(s);

					Element locationInTweet = new Element("locationInTweet");
					if (!cd.getLocationInTweet().equalsIgnoreCase("null")) {

						locationInTweet.addContent(cd.getLocationInTweet());
					}

					Element objectDetails = new Element("objectDetails");
					if (cd.getObjectDetails() == null) {
						System.out.println("IM NULL");
					}
					if (!cd.getObjectDetails().equalsIgnoreCase("null")) {

						objectDetails.addContent(cd.getObjectDetails());
					}

					Element objectName = new Element("objectName");
					if (!cd.getObjectName().equalsIgnoreCase("null")) {

						objectName.addContent(cd.getObjectName());
					}

					Element victimName = new Element("victimName");
					if (!cd.getObjectName().equalsIgnoreCase("null")) {

						victimName.addContent(cd.getVictimName());
					}

					extracted.addContent(locationInTweet);
					extracted.addContent(objectName);
					extracted.addContent(objectDetails);
					extracted.addContent(victimName);
				}

				tweet.addContent(extracted);

				doc.getRootElement().addContent(tweet);
			}
			XMLOutputter xmlOutput = new XMLOutputter();

			// display
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(outputPath));
		} catch (IOException e) {

		}
	}
	
	public static void saveXMLCA(List<Sentence> sentences, String outputPath)
			throws FileNotFoundException, XMLStreamException {
		try {
			Element dataset = new Element("dataset");
			dataset.setAttribute(new Attribute("name", "ruby-dataset"));
			Document doc = new Document(dataset);
			doc.setRootElement(dataset);
			for (Sentence s : sentences) {
				Tweet t = s.getTweets();

				Element tweet = new Element("tweet");
				tweet.setAttribute(new Attribute("id", Long.toString(t
						.getTweetID())));
				tweet.addContent(new Element("user").setText(t.getUser()));
				tweet.addContent(new Element("tweet").setText(s.getRawTweet()));

				tweet.addContent(new Element("prediction").setText(s
						.getCategory()));
				tweet.addContent(new Element("actual").setText(t.getCategory()));

				Element extracted = new Element("extracted");

				if (t.getCategory().equalsIgnoreCase("CA")) {
					CautionAndAdviceTweet ca = Binder.bindCA(s);

					Element locationInTweet = new Element("locationInTweet");
					if (!ca.getLocationInTweet().equalsIgnoreCase("null")) {

						locationInTweet.addContent(ca.getLocationInTweet());
					}

					Element advice = new Element("advice");
	
					if (!ca.getTweetAdvice().equalsIgnoreCase("null")) {
						System.out.println(ca.getTweetAdvice());
						advice.addContent(ca.getTweetAdvice());
					}


					extracted.addContent(locationInTweet);
					extracted.addContent(advice);
				
				}

				tweet.addContent(extracted);

				doc.getRootElement().addContent(tweet);
			}
			XMLOutputter xmlOutput = new XMLOutputter();

			// display
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(outputPath));
		} catch (IOException e) {

		}
	}
	
	public static void saveXMLCH(List<Sentence> sentences, String outputPath)
			throws FileNotFoundException, XMLStreamException {
		try {
			Element dataset = new Element("dataset");
			dataset.setAttribute(new Attribute("name", "ruby-dataset"));
			Document doc = new Document(dataset);
			doc.setRootElement(dataset);
			for (Sentence s : sentences) {
				Tweet t = s.getTweets();

				Element tweet = new Element("tweet");
				tweet.setAttribute(new Attribute("id", Long.toString(t
						.getTweetID())));
				tweet.addContent(new Element("user").setText(t.getUser()));
				tweet.addContent(new Element("tweet").setText(s.getRawTweet()));

				tweet.addContent(new Element("prediction").setText(s
						.getCategory()));
				tweet.addContent(new Element("actual").setText(t.getCategory()));

				Element extracted = new Element("extracted");

				if (t.getCategory().equalsIgnoreCase("CH")) {
					CallForHelpTweet ca = Binder.bindCH(s);

					Element locationInTweet = new Element("locationInTweet");
					if (!ca.getLocationInTweet().equalsIgnoreCase("null")) {

						locationInTweet.addContent(ca.getLocationInTweet());
					}

					Element victim = new Element("victimName");
	
					if (!ca.getVictimName().equalsIgnoreCase("null")) {
		
						victim.addContent(ca.getVictimName());
					}


					extracted.addContent(locationInTweet);
					extracted.addContent(victim);
				
				}

				tweet.addContent(extracted);

				doc.getRootElement().addContent(tweet);
			}
			XMLOutputter xmlOutput = new XMLOutputter();

			// display
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(outputPath));
		} catch (IOException e) {

		}
	}
	
	public static void saveXMLD(List<Sentence> sentences, String outputPath)
			throws FileNotFoundException, XMLStreamException {
		try {
			Element dataset = new Element("dataset");
			dataset.setAttribute(new Attribute("name", "ruby-dataset"));
			Document doc = new Document(dataset);
			doc.setRootElement(dataset);
			for (Sentence s : sentences) {
				Tweet t = s.getTweets();

				Element tweet = new Element("tweet");
				tweet.setAttribute(new Attribute("id", Long.toString(t
						.getTweetID())));
				tweet.addContent(new Element("user").setText(t.getUser()));
				tweet.addContent(new Element("tweet").setText(s.getRawTweet()));

				tweet.addContent(new Element("prediction").setText(s
						.getCategory()));
				tweet.addContent(new Element("actual").setText(t.getCategory()));

				Element extracted = new Element("extracted");

				if (t.getCategory().equalsIgnoreCase("D")) {
					DonationTweet d = Binder.bindD(s);

					Element locationInTweet = new Element("locationInTweet");
					if (!d.getLocationInTweet().equalsIgnoreCase("null")) {

						locationInTweet.addContent(d.getLocationInTweet());
					}

					Element name = new Element("resourceName");
	
					if (!d.getResourceName().equalsIgnoreCase("null")) {
						name.addContent(d.getResourceName());
					}
					
					Element detail = new Element("resourceDetail");
					
					if (!d.getResourceDetails().equalsIgnoreCase("null")) {
						detail.addContent(d.getResourceDetails());
					}
					
					Element victimName = new Element("victimName");
					if (!d.getVictimName().equalsIgnoreCase("null")) {

						victimName.addContent(d.getVictimName());
					}

					extracted.addContent(locationInTweet);
					extracted.addContent(name);
					extracted.addContent(detail);
					extracted.addContent(victimName);
				
				}

				tweet.addContent(extracted);

				doc.getRootElement().addContent(tweet);
			}
			XMLOutputter xmlOutput = new XMLOutputter();

			// display
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(outputPath));
		} catch (IOException e) {

		}
	}
	
	
	

	public static HashMap<Integer, Element> loadXML(String path) {
		HashMap<Integer, Element> nodes = new HashMap<Integer, Element>();
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(path);
		System.out.println("Loading XML");
		try {

			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List list = rootNode.getChildren("tweet");

			for (int i = 0; i < list.size(); i++) {
				Element node = (Element) list.get(i);
				String child = node.getAttributeValue("id");
				nodes.put(Integer.valueOf(child), node);
			}

		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}

		return nodes;
	}

}
