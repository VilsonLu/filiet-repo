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

	public static void saveXML(List<Sentence> sentences, String outputPath) {
		try {
			Element dataset = new Element("dataset");
			dataset.setAttribute(new Attribute("name", "ruby-dataset"));
			Document doc = new Document(dataset);
			doc.setRootElement(dataset);
			for (Sentence sentence : sentences) {
				Tweet t = sentence.getTweets();
				Element tweet = new Element("tweet");
				tweet.setAttribute(new Attribute("id", Long.toString(t.getTweetID())));
				tweet.addContent(new Element("user").setText(t.getUser()));
				tweet.addContent(new Element("tweet").setText(sentence.getRawTweet()));
				tweet.addContent(new Element("prediction").setText(sentence.getCategory()));
				tweet.addContent(new Element("actual").setText(t.getCategory()));

				String category = sentence.getCategory();
				if (category.equalsIgnoreCase("CA")) {
					tweet.addContent(saveXMLCA(sentence));
				} else if (category.equalsIgnoreCase("CD")) {
					tweet.addContent(saveXMLCD(sentence));
				} else if (category.equalsIgnoreCase("CH")) {
					tweet.addContent(saveXMLCH(sentence));
				} else if (category.equalsIgnoreCase("D")) {
					tweet.addContent(saveXMLD(sentence));
				}

				dataset.addContent(tweet);
			}

			XMLOutputter xmlOutput = new XMLOutputter();

			// display
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(outputPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Element saveXMLCD(Sentence sentence) {
		Element extracted = new Element("extracted");

		CasualtiesAndDamageTweet cd = Binder.bindCD(sentence);

		Element locationInTweet = new Element("locationInTweet");
		if (!cd.getLocationInTweet().equalsIgnoreCase("null")) {

			locationInTweet.addContent(cd.getLocationInTweet());
		}

		Element objectDetails = new Element("objectDetails");
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

		return extracted;
	}

	private static Element saveXMLCA(Sentence sentence) {

		Element extracted = new Element("extracted");

		CautionAndAdviceTweet ca = Binder.bindCA(sentence);

		Element locationInTweet = new Element("locationInTweet");
		if (!ca.getLocationInTweet().equalsIgnoreCase("null")) {

			locationInTweet.addContent(ca.getLocationInTweet());
		}

		Element advice = new Element("advice");

		if (!ca.getTweetAdvice().equalsIgnoreCase("null")) {
			advice.addContent(ca.getTweetAdvice());
		}

		extracted.addContent(locationInTweet);
		extracted.addContent(advice);

		return extracted;

	}

	private static Element saveXMLCH(Sentence sentence) {

		Element extracted = new Element("extracted");

		CallForHelpTweet ca = Binder.bindCH(sentence);

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

		return extracted;

	}

	private static Element saveXMLD(Sentence sentence) {

		Element extracted = new Element("extracted");

		DonationTweet d = Binder.bindD(sentence);

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

		return extracted;
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
