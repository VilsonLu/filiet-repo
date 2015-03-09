package preprocess.normalizer;

import normapi.NormAPI;

public class NormApiImpl implements NormalizerInterface {

	@Override
	public String execute(String tweet) {
		return NormAPI.normalize_Text(tweet);
	}

}
