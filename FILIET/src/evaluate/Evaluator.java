package evaluate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ontology.model.CallForHelpTweet;
import ontology.model.CasualtiesAndDamageTweet;
import ontology.model.CautionAndAdviceTweet;
import ontology.model.DonationTweet;

import org.jdom.Element;

import com.csvreader.CsvReader;

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

	public static final int CA = 0;
	public static final int CD = 1;
	public static final int CH = 2;
	public static final int D = 3;
	public static final int O = 4;

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

		if (reference == null) {
			reference = "";
		}

		if (hypothesis == null) {
			hypothesis = "";
		}

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
		if (size != 0) {
			for (int i = 0; i < size; i++) {
				if (ref[i].equalsIgnoreCase(hypo[i])) {
					scores[CORRECT] += 1;
				} else {
					scores[SUBSTITUTION] += 1;
				}
			}
		} else {
			if (ref.equals(hypo)) {
				scores[CORRECT] += 1;
			} else {
				scores[SUBSTITUTION] += hypo.length;
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
		int[] sum = new int[mat1.length];

		for (int i = 0; i < MATRIX; i++) {
			sum[i] = mat1[i] + mat2[i];
		}

		return sum;
	}

	public void evaluateDatasetCD(List<Sentence> hypothesis,
			String referencePath) {

		int scoreLocation[] = new int[] { 0, 0, 0, 0 };
		int scoreObjectName[] = new int[] { 0, 0, 0, 0 };
		int scoreObjectDetail[] = new int[] { 0, 0, 0, 0 };
		int scoreVictimName[] = new int[] { 0, 0, 0, 0 };

		Map<Integer, String[]> references = new HashMap<Integer, String[]>();

		// load the annotations
		try {
			CsvReader csvReader = new CsvReader(referencePath);
			csvReader.readHeaders();

			while (csvReader.readRecord()) {

				String[] annotations = new String[4];
				int tweetID = Integer.valueOf(csvReader.get("TweetID"));
				annotations[0] = csvReader.get("LocationInTweet");
				annotations[1] = csvReader.get("ObjectName");
				annotations[2] = csvReader.get("ObjectDetail");
				annotations[3] = csvReader.get("VictimName");

				references.put(tweetID, annotations);
			}

			csvReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// evaluate
		for (Sentence test : hypothesis) {
			int tweetID = (int) (long) test.getTweets().getTweetID();
			CasualtiesAndDamageTweet cd = Binder.bindCD(test);
			String[] reference = references.get(tweetID);
			if (reference != null) {
				System.out.println(tweetID);
				// check if there is no location
				if (reference[0] == null) {
					reference[0] = "";
				}

				scoreLocation = addMatrix(scoreLocation,
						getScore(reference[0], cd.getLocationInTweet()));
				scoreObjectName = addMatrix(scoreObjectName,
						getScore(reference[1], cd.getObjectName()));
				scoreObjectDetail = addMatrix(scoreObjectDetail,
						getScore(reference[2], cd.getObjectDetails()));
				scoreVictimName = addMatrix(scoreVictimName,
						getScore(reference[3], cd.getVictimName()));
			}

		}

		System.out.println("Final Score");

		System.out.println("Precision (Location): "
				+ getPrecision(scoreLocation));
		System.out.println("Recall (Location): " + getRecall(scoreLocation));
		System.out.println("F-measure (Location): "
				+ getFMeasure(getPrecision(scoreLocation),
						getRecall(scoreLocation)));
		printMatrix(scoreLocation);

		System.out.println("Precision (Object Name): "
				+ getPrecision(scoreObjectName));
		System.out.println("Recall (Object Name): "
				+ getRecall(scoreObjectName));
		System.out.println("F-measure (bject Name): "
				+ getFMeasure(getPrecision(scoreObjectName),
						getRecall(scoreObjectName)));
		printMatrix(scoreObjectName);

		System.out.println("Precision (Object Detail): "
				+ getPrecision(scoreObjectDetail));
		System.out.println("Recall (Object Detail): "
				+ getRecall(scoreObjectDetail));
		System.out.println("F-measure (Object Detail): "
				+ getFMeasure(getPrecision(scoreObjectDetail),
						getRecall(scoreObjectDetail)));
		printMatrix(scoreObjectDetail);

		System.out.println("Precision (Victim Name): "
				+ getPrecision(scoreVictimName));
		System.out.println("Recall (Victim Name): "
				+ getRecall(scoreVictimName));
		System.out.println("F-measure (Victim Name): "
				+ getFMeasure(getPrecision(scoreVictimName),
						getRecall(scoreVictimName)));

		printMatrix(scoreVictimName);

	}

	public void evaluateDatasetCA(List<Sentence> hypothesis,
			String referencePath) {

		int scoreLocation[] = new int[] { 0, 0, 0, 0 };
		int scoreTweetAdvice[] = new int[] { 0, 0, 0, 0 };

		Map<Integer, String[]> references = new HashMap<Integer, String[]>();

		// load the annotations
		try {
			CsvReader csvReader = new CsvReader(referencePath);
			csvReader.readHeaders();

			while (csvReader.readRecord()) {

				String[] annotations = new String[2];
				int tweetID = Integer.valueOf(csvReader.get("TweetID"));
				annotations[0] = csvReader.get("LocationInTweet");
				annotations[1] = csvReader.get("TweetAdvice");

				references.put(tweetID, annotations);
			}

			csvReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// evaluate
		for (Sentence test : hypothesis) {
			int tweetID = (int) (long) test.getTweets().getTweetID();

			CautionAndAdviceTweet ca = Binder.bindCA(test);
			System.out.println("Hypothesis: " + tweetID);
			String[] reference = references.get(tweetID);
			if (reference != null) {
				System.out.println(tweetID);
				// check if there is no location
				if (reference[0] == null) {
					reference[0] = "";
				}

				if (reference[1] == null) {
					reference[1] = "";
				}

				scoreLocation = addMatrix(scoreLocation,
						getScore(reference[0], ca.getLocationInTweet()));
				scoreTweetAdvice = addMatrix(scoreTweetAdvice,
						getScore(reference[1], ca.getTweetAdvice()));

			}

		}

		System.out.println("Final Score");

		System.out.println("Precision (Location): "
				+ getPrecision(scoreLocation));
		System.out.println("Recall (Location): " + getRecall(scoreLocation));

		System.out.println("F-Measure(Location)" + getFMeasure(getPrecision(scoreLocation), getRecall(scoreLocation)));
		printMatrix(scoreLocation);

		System.out.println("Precision (Advice): "
				+ getPrecision(scoreTweetAdvice));
		System.out.println("Recall (Advice): " + getRecall(scoreTweetAdvice));
		System.out.println("F-Measure(Location)" + getFMeasure(getPrecision(scoreTweetAdvice), getRecall(scoreTweetAdvice)));

		printMatrix(scoreTweetAdvice);

	}

	public void evaluateDatasetD(List<Sentence> hypothesis, String referencePath) {

		int scoreLocation[] = new int[] { 0, 0, 0, 0 };
		int scoreResourceName[] = new int[] { 0, 0, 0, 0 };
		int scoreResourceDetail[] = new int[] { 0, 0, 0, 0 };
		int scoreVictimName[] = new int[] { 0, 0, 0, 0 };

		Map<Integer, String[]> references = new HashMap<Integer, String[]>();

		// load the annotations
		try {
			CsvReader csvReader = new CsvReader(referencePath);
			csvReader.readHeaders();

			while (csvReader.readRecord()) {

				String[] annotations = new String[4];
				int tweetID = Integer.valueOf(csvReader.get("TweetID"));
				annotations[0] = csvReader.get("LocationInTweet");
				annotations[1] = csvReader.get("ResourceName");
				annotations[2] = csvReader.get("ResourceDetail");
				annotations[3] = csvReader.get("VictimName");

				references.put(tweetID, annotations);
			}

			csvReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// evaluate
		for (Sentence test : hypothesis) {
			int tweetID = (int) (long) test.getTweets().getTweetID();
			DonationTweet d = Binder.bindD(test);
			String[] reference = references.get(tweetID);
			if (reference != null) {

				// check if there is no location
				if (reference[0] == null) {
					reference[0] = "";
				}
				if (reference[1] == null) {
					reference[1] = "";
				}
				if (reference[2] == null) {
					reference[2] = "";
				}
				if (reference[3] == null) {
					reference[3] = "";
				}

				scoreLocation = addMatrix(scoreLocation,
						getScore(reference[0], d.getLocationInTweet()));
				scoreResourceName = addMatrix(scoreResourceName,
						getScore(reference[1], d.getResourceName()));
				scoreResourceDetail = addMatrix(scoreResourceDetail,
						getScore(reference[2], d.getResourceDetails()));
				scoreVictimName = addMatrix(scoreVictimName,
						getScore(reference[3], d.getVictimName()));
			}

		}

		System.out.println("Final Score");

		System.out.println("Precision (Location): "
				+ getPrecision(scoreLocation));
		System.out.println("Recall (Location): " + getRecall(scoreLocation));
		System.out.println("F-measure (Location): "
				+ getFMeasure(getPrecision(scoreLocation),
						getRecall(scoreLocation)));
		printMatrix(scoreLocation);

		System.out.println("Precision (Resource Name): "
				+ getPrecision(scoreResourceName));
		System.out.println("Recall (Resource Name): "
				+ getRecall(scoreResourceName));
		System.out.println("F-measure (Resource Name): "
				+ getFMeasure(getPrecision(scoreResourceName),
						getRecall(scoreResourceName)));
		printMatrix(scoreResourceName);

		System.out.println("Precision (Resource Detail): "
				+ getPrecision(scoreResourceDetail));
		System.out.println("Recall (Resource Detail): "
				+ getRecall(scoreResourceDetail));
		System.out.println("F-measure (Resource Detail): "
				+ getFMeasure(getPrecision(scoreResourceDetail),
						getRecall(scoreResourceDetail)));
		printMatrix(scoreResourceDetail);

		System.out.println("Precision (Victim Name): "
				+ getPrecision(scoreVictimName));
		System.out.println("Recall (Victim Name): "
				+ getRecall(scoreVictimName));
		System.out.println("F-measure (Victim Name): "
				+ getFMeasure(getPrecision(scoreVictimName),
						getRecall(scoreVictimName)));

		printMatrix(scoreVictimName);

	}

	public void evaluateDatasetCH(List<Sentence> hypothesis,
			String referencePath) {

		int scoreLocation[] = new int[] { 0, 0, 0, 0 };
		int scoreVictimName[] = new int[] { 0, 0, 0, 0 };

		Map<Integer, String[]> references = new HashMap<Integer, String[]>();

		// load the annotations
		try {
			CsvReader csvReader = new CsvReader(referencePath);
			csvReader.readHeaders();

			while (csvReader.readRecord()) {

				String[] annotations = new String[4];
				int tweetID = Integer.valueOf(csvReader.get("TweetID"));
				annotations[0] = csvReader.get("LocationInTweet");
				annotations[1] = csvReader.get("victimName");

				references.put(tweetID, annotations);
			}

			csvReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// evaluate
		for (Sentence test : hypothesis) {
			int tweetID = (int) (long) test.getTweets().getTweetID();
			CallForHelpTweet ch = Binder.bindCH(test);
			String[] reference = references.get(tweetID);
			if (reference != null) {

				// check if there is no location
				if (reference[0] == null) {
					reference[0] = "";
				}
				if (reference[1] == null) {
					reference[1] = "";
				}

				scoreLocation = addMatrix(scoreLocation,
						getScore(reference[0], ch.getLocationInTweet()));
				scoreVictimName = addMatrix(scoreVictimName,
						getScore(reference[1], ch.getVictimName()));
			}

		}

		System.out.println("Final Score");

		System.out.println("Precision (Location): "
				+ getPrecision(scoreLocation));
		System.out.println("Recall (Location): " + getRecall(scoreLocation));
		System.out.println("F-measure (Location): "
				+ getFMeasure(getPrecision(scoreLocation),
						getRecall(scoreLocation)));
		printMatrix(scoreLocation);

		System.out.println("Precision (Victim Name): "
				+ getPrecision(scoreVictimName));
		System.out.println("Recall (Victim Name): "
				+ getRecall(scoreVictimName));
		System.out.println("F-measure (Victim Name): "
				+ getFMeasure(getPrecision(scoreVictimName),
						getRecall(scoreVictimName)));

		printMatrix(scoreVictimName);

	}

	public void evaluateClassifier(List<Sentence> sentences) {
		int[] sumCA = new int[] { 0, 0, 0, 0, 0 };
		int[] sumCH = new int[] { 0, 0, 0, 0, 0 };
		int[] sumD = new int[] { 0, 0, 0, 0, 0 };
		int[] sumCD = new int[] { 0, 0, 0, 0, 0 };
		int[] sumO = new int[] { 0, 0, 0, 0, 0 };

		System.out.println(sentences.size());
		for (Sentence sentence : sentences) {

			int temp[] = new int[] { 0, 0, 0, 0, 0 };
			String actual = sentence.getTweets().getCategory();
			String prediction = sentence.getCategory();

			System.out.println(actual + "\t" + prediction);
		}

	}

}
