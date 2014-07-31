package preprocess;

public class POSTagger {
	private POSTaggerInterface strategy;
	
	public POSTagger(POSTaggerInterface strategy){
		this.strategy = strategy;
	}
	
	public String[] executeStrategy(String[] text){
		return this.strategy.execute(text);
	}
}
