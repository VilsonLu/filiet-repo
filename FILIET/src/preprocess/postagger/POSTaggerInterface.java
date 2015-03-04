package preprocess.postagger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import support.model.Sentence;
import support.model.Token;

public interface POSTaggerInterface {
	Sentence execute(Sentence tokens);
}

