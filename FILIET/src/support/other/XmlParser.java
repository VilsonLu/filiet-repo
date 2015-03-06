package support.other;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import support.model.Sentence;
import support.model.Tweet;

public class XmlParser {

	public static void saveXML(List<Sentence> sentences, String outputPath)
			throws FileNotFoundException, XMLStreamException {
		try{
			Element dataset = new Element("dataset");
			dataset.setAttribute(new Attribute("name","ruby-dataset"));
			Document doc = new Document(dataset);
			doc.setRootElement(dataset);
			for(Sentence s:sentences){
				Tweet t = s.getTweets();
				Element tweet = new Element("tweet");
				tweet.setAttribute(new Attribute("id", Long.toString(t.getTweetID())));
				tweet.addContent(new Element("user").setText(t.getUser()));
				tweet.addContent(new Element("tweet").setText(s.getRawTweet()));
				
				if(s.getCategory() == null){
					System.out.println("Category is null");
				}
				
				tweet.addContent(new Element("prediction").setText(s.getCategory()));
				tweet.addContent(new Element("actual").setText(t.getCategory()));
				doc.getRootElement().addContent(tweet);
			}
			XMLOutputter xmlOutput = new XMLOutputter();
			 
			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(outputPath));
		} catch(IOException e){
			
		}
	}



}
