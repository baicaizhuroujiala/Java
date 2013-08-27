//Write a method to decide if two strings are anagrams or not.
public class Main {

	public static void main(String[] args) {
		String a = "1123";
		String b = "2113";
		String c = "1223";
		String d = "2113";
		System.out.println(anagrams(a,b));
		System.out.println(anagrams(c,d));

	}
	
	public static boolean anagrams(String a, String b){
		char c1[] = a.toCharArray();
		char c2[] = b.toCharArray();
		java.util.Arrays.sort(c1);
		java.util.Arrays.sort(c2);
		String m = new String(c1);
		String n = new String(c2);
		return m.equals(n);

	}

}
