//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//Implement an algorithm to determine if a string has all unique characters. 
//What if you can not use additional data structures?

public class Main {

	public static void main(String[] args) {
		System.out.println(isUnique("aabc"));
	}
	
	public static boolean isUnique(String s){
		boolean[] b = new boolean[256];
		for(boolean t: b)
			System.out.println(t);
		for(int i=0; i<b.length; i++){
			int t = s.charAt(i);
			if (b[t]==true)
				return false;
			else
				b[t]=true;
		}
		return true;
	}


	
	
//	static char a[] = new char[100];
//	static int index = 0;
//
//	public static void main(String[] args) {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//	    try {
//			String str = br.readLine();
//			char s[] = new char[str.length()];
//			s = str.toCharArray();
//			for(int i =0 ; i<s.length; i++){
////				System.out.println("i");
//				if(find(s[i])){
//					System.out.println("Not Unique");
//					return;
//				}
//				else add(s[i]);
//			}
//			System.out.println("Unique");
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private static void add(char c) {
////		System.out.println("add");
//		a[index] = c;
//		index ++;
//	}
//
//	private static boolean find(char c) {
////		System.out.println("find");
//		for (int i = 0; i<index; i++){
//			if (a[i] == c) {
////1123				System.out.println("true");
//				return true;
//			}
//		}
//		return false;
//	}

}
