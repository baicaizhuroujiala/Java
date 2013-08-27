//Design an algorithm and write code to remove the duplicate characters in a string without using any 
//additional buffer.
import java.awt.event.*;

public class Main {

	public static String removeDuplicates(String a) {
		char s[] = a.toCharArray();
		int currentPosition = 0;	//record current position of char[] without duplicates
		int tail = 0;				//record the character deal with now.
		for(tail = 0; tail < s.length; tail ++) {	//loop all the characters
			int i;
			for(i = 0; i<tail; i++){		//loop all the characters before tail
				if (s[i] == s[tail]) {		//if has duplicates 
					break;
				}
			}
			if(i == tail) {					//if do not have duplicates
				s[currentPosition] = s[tail];
				currentPosition++;
			}
		}
		System.out.println(currentPosition);
		return new String(s).substring(0, currentPosition);
	}
	
	public static void main(String[] args) {
		String a = "abcd";
		String b = "abac";
		System.out.println(removeDuplicates(a));
		System.out.println(removeDuplicates(b));

	}

}
