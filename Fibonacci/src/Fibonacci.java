
public class Fibonacci {
	static final int a = 100;
	static final String title = "Fibonacci";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a = 1;
		int b = 1;
		int s = 1;
		int[] f = new int[20];
		System.out.println(title);
		f[0]=a;
		String sf="0";
		System.out.println(s+":"+a);
		for (int i=1; b < a ; i++){	
			s++;
			f[i]=b;
			if (b % 2 == 0){
				System.out.println(s+":"+b+" *");
			}
			else {
				System.out.println(s+":"+b);
			}
			b = a + b;
			a = b - a;
		}
		for (int i=0; f[i]!=0 ; i++){
			System.out.println("_"+f[i]);
			sf+=" "+f[i];
		}
		System.out.println(sf);
	}
}

