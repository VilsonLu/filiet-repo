package preprocess.ner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import support.model.Sentence;
import support.model.Token;

public interface NERInterface {
	public Sentence execute(Sentence tweet);
}

