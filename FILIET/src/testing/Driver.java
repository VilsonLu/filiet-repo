package testing;

import java.io.IOException;

public class Driver {
	public static void main(String[] args) throws IOException{
		String path = "./resources/tweets/Testing/Test.csv";
		Reader.readCSVFile(path);
	}
}
