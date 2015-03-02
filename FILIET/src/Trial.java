
public class Trial {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String num = "200,000.00";
		String separator = ",";
		num.replaceAll("[^0-9]", "");
		System.out.println(num.replaceAll("[^0-9]", ""));
	}

}
