package preprocess.normalizer;

import support.model.Sentence;
import normapi.*;

public class NormApiImpl implements NormalizerInterface {

	@Override
	public String execute(String tweet) {
		return NormAPI.normalizeDSA_Text(tweet);
	}

}
