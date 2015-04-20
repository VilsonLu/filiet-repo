package evaluate;

public class EvaluatorClassifier {

	private int[][] matrix = new int[5][5];

	public EvaluatorClassifier() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = 0;
			}
		}
	}

	public void evaluate(String actual, String hypothesis) {
		if (actual.equalsIgnoreCase("CA")) {
			if (hypothesis.equalsIgnoreCase("CA"))
				matrix[0][0]++;
			else if (hypothesis.equalsIgnoreCase("CD"))
				matrix[0][1]++;
			else if (hypothesis.equalsIgnoreCase("CH"))
				matrix[0][2]++;
			else if (hypothesis.equalsIgnoreCase("D"))
				matrix[0][3]++;
			else if (hypothesis.equalsIgnoreCase("O"))
				matrix[0][4]++;
		} else if (actual.equalsIgnoreCase("CD")) {
			if (hypothesis.equalsIgnoreCase("CA"))
				matrix[1][0]++;
			else if (hypothesis.equalsIgnoreCase("CD"))
				matrix[1][1]++;
			else if (hypothesis.equalsIgnoreCase("CH"))
				matrix[1][2]++;
			else if (hypothesis.equalsIgnoreCase("D"))
				matrix[1][3]++;
			else if (hypothesis.equalsIgnoreCase("O"))
				matrix[1][4]++;
		} else if (actual.equalsIgnoreCase("CH")) {
			if (hypothesis.equalsIgnoreCase("CA"))
				matrix[2][0]++;
			else if (hypothesis.equalsIgnoreCase("CD"))
				matrix[2][1]++;
			else if (hypothesis.equalsIgnoreCase("CH"))
				matrix[2][2]++;
			else if (hypothesis.equalsIgnoreCase("D"))
				matrix[2][3]++;
			else if (hypothesis.equalsIgnoreCase("O"))
				matrix[2][4]++;
		} else if (actual.equalsIgnoreCase("D")) {
			if (hypothesis.equalsIgnoreCase("CA"))
				matrix[3][0]++;
			else if (hypothesis.equalsIgnoreCase("CD"))
				matrix[3][1]++;
			else if (hypothesis.equalsIgnoreCase("CH"))
				matrix[3][2]++;
			else if (hypothesis.equalsIgnoreCase("D"))
				matrix[3][3]++;
			else if (hypothesis.equalsIgnoreCase("O"))
				matrix[3][4]++;
		} else if (actual.equalsIgnoreCase("O")) {
			if (hypothesis.equalsIgnoreCase("CA"))
				matrix[4][0]++;
			else if (hypothesis.equalsIgnoreCase("CD"))
				matrix[4][1]++;
			else if (hypothesis.equalsIgnoreCase("CH"))
				matrix[4][2]++;
			else if (hypothesis.equalsIgnoreCase("D"))
				matrix[4][3]++;
			else if (hypothesis.equalsIgnoreCase("O"))
				matrix[4][4]++;
		}

	}

	public void printMatrix() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}

			System.out.println();
		}
	}

}
