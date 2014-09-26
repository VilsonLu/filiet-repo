package preprocess.tokenizer;

import gate.Gate;
import gate.util.GateException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import model.Sentence;
import model.Token;
import cmu.arktweetnlp.Twokenize;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

public interface TokenizerInterface {
	Sentence execute(String text);
}

