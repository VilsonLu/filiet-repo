package preprocess.postagger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.Sentence;
import model.Token;

public interface POSTaggerInterface {
	Sentence execute(Sentence tokens);
}

