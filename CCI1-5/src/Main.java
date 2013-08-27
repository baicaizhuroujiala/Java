
public class Main {

	public static String replace1(String s){
		char a[] = s.toCharArray();
		int index[] = new int[a.length];
		int offset = 0;
		for(int i = 0; i<a.length; i++){
			index[i] = i + offset;
			if (a[i] == ' ') {
				offset += 2;
			}
		}
		char b[] = new char[a.length+offset];
		for(int i = 0 ; i < a.length ; i++){
			if (a[i] == ' '){
				b[index[i]] = '%';
				b[index[i]+1] = '2';
				b[index[i]+2] = '0';
			}
			else {
				b[index[i]] = a[i];
			}
		}
		return new String(b);
	}
	
	public static String replace2(String s){
		char[] a = s.toCharArray();
		int count = 0;
		for ( int i = 0 ; i < a.length ; i++){
			if (a[i] == ' '){
				count += 3;
			}
			else {
				count ++;
			}
		}
		char[] b = new char[count];
		int offset = 0;
		for (int i = 0; i < a.length; i++){
			if (a[i] == ' '){
				b[i+offset] = '%';
				b[i+offset+1] = '2';
				b[i+offset+2] = '0';
				offset += 2;
			}
			else {
				b[i+offset] = a[i];
			}
		}
		return new String(b);
	}
	
	public static void main(String[] args) {
		String s = "abc def";
		System.out.println(replace2(s));
	}

}
