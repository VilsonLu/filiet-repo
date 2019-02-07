package OntologyRetriever;

import java.util.ArrayList;

public class LabelledDPVPair {
	String dpvLabel;
	ArrayList<DPVPair> dpvList;
	
	public String getDpvLabel() {
		return dpvLabel;
	}
	public void setDpvLabel(String dpvLabel) {
		this.dpvLabel = dpvLabel;
	}
	public ArrayList<DPVPair> getDpvList() {
		return dpvList;
	}
	public void setDpvList(ArrayList<DPVPair> dpvList) {
		this.dpvList = dpvList;
	}
}
