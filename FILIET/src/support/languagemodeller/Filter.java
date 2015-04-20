package support.languagemodeller;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filter {
	public static boolean hasSpecialCharacters(String text){
		
		String punctuations = "(?![@',&]\\#)\\p{Punct}";
		Pattern pattern = Pattern.compile(punctuations);
		
		Matcher m = pattern.matcher(text);
		return m.find();
	}
	
	public static boolean hasLinks(String text){
		return text.contains("http");
	}
	
	public static boolean hasDiacritic(String text){
		
		String punctuations = "[^\\p{ASCII}]";
		Pattern pattern = Pattern.compile(punctuations);
		Matcher m = pattern.matcher(text);
		return m.find();
	}
	
	public static boolean isNumeric(String str) {
		String num = str.replaceAll("[^0-9]", "");

		try {
			Double x = Double.valueOf(num);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}
	
	public static String removeNonAlphaNumeric(String str){
		return str.replaceAll("[^A-Za-z0-9 ]", "");
	}
}
