package evaluate;

public class EvaluatorDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Evaluator evaluate = new Evaluator();
		String reference = "";
		String hypothesis = "";
		
		System.out.println(reference.length());
		int[] scores = evaluate.getScore(reference, hypothesis);
		double precision = evaluate.getPrecision(scores);
		double recall = evaluate.getRecall(scores);
		evaluate.printMatrix(scores);
		System.out.println("Precision: " + precision);
		System.out.println("Recall: "+ recall);
		
	}

}
