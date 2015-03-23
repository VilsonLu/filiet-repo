package support.languagemodeller;

public class FilterDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Filter.hasDiacritic("\"@"));
		System.out.println(!Filter.hasSpecialCharacters("aa"));
	}

}
