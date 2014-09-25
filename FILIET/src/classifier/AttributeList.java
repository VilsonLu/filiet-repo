package classifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import weka.core.Attribute;

/*
 * This class contains the attributes of an instance
 */

public class AttributeList {
	private List<Attribute> Attributes;
	
	public AttributeList(){
		Attributes = new ArrayList<Attribute>();
	}
	
	public void AddAttribute(String attributeName){
		Attributes.add(new Attribute(attributeName));
	}
	
}
