package classifier;

import java.io.IOException;

public class Trial {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String path = "./resources/tweets/test-extracted/Batch 2/test-Combined.csv";
		InstanceBuilder ib = new InstanceBuilder();
		ib.buildTrainingData(path);
	}

}
