//Assume you have a method isSubstring which checks if one word is a substring of another. 
//Given two strings, s1 and s2, write code to check if s2 is a rotation of s1 using only 
//one call to isSubstring (i.e., "waterbottle" is a rotation of "erbottlewat").
//Check if length(s1) == length(s2). If not, return false.
//Else, concatenate s1 with itself and see whether s2 is substring of the result. input: s1 = apple, s2 = pleap ==> apple is a substring of pleappleap
//input: s1 = apple, s2 = ppale ==> apple is not a substring of ppaleppale
public class Main {

	public static boolean findRotation(String s1, String s2){
		if(s1.length()!=s2.length()){
			return false;
		}
		else {
			String a = s1+s1;
			return isSubString(a, s2);
		}
	}
	
	public static boolean isSubString(String s, String sub){
		return s.contains(sub);
	}
	public static void main(String[] args) {
		String a = "abcd";
		String b = "cdab";
		System.out.println(findRotation(a,b));
	}

}
