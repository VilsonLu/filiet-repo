package preprocess.disastertagger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.Sentence;
import model.Token;

public interface DisasterTaggerInterface {
	Sentence execute(Sentence tokens);
}
