package experiment;

public class Demo {

	public static void main(String[] args) {
		
		String str = "My name is Arun Motoori. I am the founder at QAFox.com. I love creating content on Software Testing and Tools.";
		String[] ar = str.split(".com");
		String s = ar[0];
		String[] arr = s.split("at ");
		System.out.println(arr[1]);

	}

}
