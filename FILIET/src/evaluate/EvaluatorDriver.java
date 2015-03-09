package evaluate;

public class EvaluatorDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Evaluator evaluate = new Evaluator();
		String reference = "hello !!";
		String hypothesis = "hello !!";
		
		int scores[] = evaluate.getScore(reference, hypothesis);
		System.out.println("CORRECT: " + scores[Evaluator.CORRECT]);
		System.out.println("SUBSTITUTION: " + scores[Evaluator.SUBSTITUTION]);
		System.out.println("INSERTION: " + scores[Evaluator.INSERTION]);
		System.out.println("DELETION: " + scores[Evaluator.DELETION]);
		System.out.println("Performance Scores: ");
		System.out.println("Precision: " + evaluate.getPrecision(scores));
		System.out.println("Recall: " + evaluate.getRecall(scores));
		System.out.println("F-Measure: " + evaluate.getFMeasure(evaluate.getPrecision(scores), evaluate.getRecall(scores)));
	}

}
