package preprocess;

public class DisasterTagger {
	private DisasterTaggerInterface strategy;
	
	public DisasterTagger(DisasterTaggerInterface strategy){
		this.strategy = strategy;
	}
	
	public String[] executeStrategy(String[] tokens){
		return this.strategy.execute(tokens);
	}
}
