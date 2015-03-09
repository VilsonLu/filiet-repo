package evaluate;

import java.util.HashMap;
import java.util.List;

import ontology.model.CasualtiesAndDamageTweet;

import org.jdom.Element;

import binder.Binder;
import support.model.Sentence;
import support.model.Tweet;
import support.other.XmlParser;

public class Evaluator {

	public static final int CORRECT = 0;
	public static final int SUBSTITUTION = 1;
	public static final int INSERTION = 2;
	public static final int DELETION = 3;
	public static final int MATRIX = 4;

	public Evaluator() {
	}

	/**
	 * 
	 * @param reference
	 * @param hypothesis
	 */
	public int[] getScore(String reference, String hypothesis) {
		// initialization
		int scores[] = new int[MATRIX];

		String[] ref = reference.split(" ");
		String[] hypo = hypothesis.split(" ");

		int size = 0;
		for (int i = 0; i < scores.length; i++) {
			scores[i] = 0;
		}

		// insertions and deletions
		if (ref.length > hypo.length) {
			scores[DELETION] = ref.length - hypo.length;
			size = hypo.length;
		} else if (hypo.length > ref.length) {
			scores[INSERTION] = hypo.length - ref.length;
			size = ref.length;
		} else {
			size = ref.length;
		}

		// correct and substitution
		for (int i = 0; i < size; i++) {
			if (ref[i].equalsIgnoreCase(hypo[i])) {
				scores[CORRECT] += 1;
			} else {
				scores[SUBSTITUTION] += 1;
			}
		}

		return scores;

	}

	public double getPrecision(int[] scores) {

		double csi = scores[CORRECT] + scores[SUBSTITUTION] + scores[INSERTION];
		double precision = (double) scores[CORRECT] / csi;

		return precision;
	}

	public double getRecall(int[] scores) {

		double csd = scores[CORRECT] + scores[SUBSTITUTION] + scores[DELETION];
		double recall = (double) scores[CORRECT] / csd;
		return recall;
	}

	public double getFMeasure(double precision, double recall) {
		return 2 * ((precision * recall) / (precision + recall));
	}

	public void printMatrix(int[] scores) {
		System.out.println("CORRECT: " + scores[Evaluator.CORRECT]);
		System.out.println("SUBSTITUTION: " + scores[Evaluator.SUBSTITUTION]);
		System.out.println("INSERTION: " + scores[Evaluator.INSERTION]);
		System.out.println("DELETION: " + scores[Evaluator.DELETION]);
	}

	public int[] addMatrix(int[] mat1, int[] mat2) {
		int[] sum = new int[MATRIX];
		sum[CORRECT] = mat1[CORRECT] + mat2[CORRECT];
		sum[SUBSTITUTION] = mat1[SUBSTITUTION] + sum[SUBSTITUTION];
		sum[INSERTION] = mat1[INSERTION] + mat2[INSERTION];
		sum[DELETION] = mat1[DELETION] + mat2[DELETION];
		return sum;
	}

	public void evaluateDatasetCD(List<Sentence> hypothesis,
			String referencePath) {

		int scoreLocation[] = new int[] { 0, 0, 0, 0 };
		int scoreObjectName[] = new int[] { 0, 0, 0, 0 };
		int scoreObjectDetail[] = new int[] { 0, 0, 0, 0 };
		int scoreVictimName[] = new int[] { 0, 0, 0, 0 };

		HashMap<Integer, Element> nodes = XmlParser.loadXML(referencePath);
		for (Sentence s : hypothesis) {
			Tweet t = s.getTweets();
			Element node = nodes.get((int) t.getTweetID());
			CasualtiesAndDamageTweet cd = Binder.bindCD(s);
			if (node != null) {
				Element extracted = node.getChild("extracted");
				String location = extracted.getChildText("locationInTweet");
				scoreLocation = addMatrix(scoreLocation,
						getScore(location, cd.getLocationInTweet()));
				String objectDetails = extracted.getChildText("objectDetail");

				if (objectDetails == null) {
					objectDetails = "";
				}

				if (cd.getObjectDetails().equalsIgnoreCase("null")) {
					cd.setObjectDetails("");
				}

				scoreObjectDetail = addMatrix(scoreObjectDetail,
						getScore(objectDetails, cd.getObjectDetails()));
				String objectName = extracted.getChildText("objectName");

				if (objectName == null) {
					objectName = "";
				}

				if (cd.getObjectName().equalsIgnoreCase("null")) {
					cd.setObjectName("");
				}

				scoreObjectName = addMatrix(scoreObjectName,
						getScore(objectName, cd.getObjectName()));

				String victimName = extracted.getChildText("victimName");

				if (victimName == null) {
					victimName = "";
				}

				if (cd.getVictimName().equalsIgnoreCase("null")) {
					cd.setVictimName("");
				}

				scoreVictimName = addMatrix(scoreVictimName,
						getScore(victimName, cd.getVictimName()));
				System.out.println("<" + victimName.length() + "> "
						+ cd.getVictimName());

			}

			// printMatrix(scoreVictimName);
			// System.out.println("Text" + t.getTweet());
		}

		System.out.println("Location");
		// printMatrix(scoreLocation);
		System.out.println("Precision: " + getPrecision(scoreLocation));
		System.out.println("Recall: " + getRecall(scoreLocation));
		System.out.println("F-Measure: "
				+ getFMeasure(getPrecision(scoreLocation),
						getRecall(scoreLocation)));

		System.out.println("Object Name");
		// printMatrix(scoreObjectName);
		System.out.println("Precision: " + getPrecision(scoreObjectName));
		System.out.println("Recall: " + getRecall(scoreObjectName));
		System.out.println("F-Measure: "
				+ getFMeasure(getPrecision(scoreObjectName),
						getRecall(scoreObjectName)));

		System.out.println("Object Detail");
		// printMatrix(scoreObjectDetail);
		System.out.println("Precision: " + getPrecision(scoreObjectDetail));
		System.out.println("Recall: " + getRecall(scoreObjectDetail));
		System.out.println("F-Measure: "
				+ getFMeasure(getPrecision(scoreObjectDetail),
						getRecall(scoreObjectDetail)));

		System.out.println("Victim Name");
		//printMatrix(scoreVictimName);
		System.out.println("Precision: " + getPrecision(scoreVictimName));
		System.out.println("Recall: " + getRecall(scoreVictimName));
		System.out.println("F-Measure: "
				+ getFMeasure(getPrecision(scoreVictimName),
						getRecall(scoreVictimName)));
	}
}
