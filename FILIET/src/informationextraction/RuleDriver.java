package informationextraction;

import java.io.IOException;

public class RuleDriver {
	public static void main(String[] args) {
		PatternMatching pm = new PatternMatching();
		try {
			pm.loadRules();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
