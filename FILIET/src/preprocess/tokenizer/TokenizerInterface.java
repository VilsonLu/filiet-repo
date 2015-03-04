package preprocess.tokenizer;

import gate.Gate;
import gate.util.GateException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cmu.arktweetnlp.Twokenize;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import support.model.Sentence;
import support.model.Token;

public interface TokenizerInterface {
	Sentence execute(String text);
}

