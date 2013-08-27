//Given an image represented by an NxN matrix, where each pixel in the image is 4 bytes, 
//write a method to rotate the image by 90 degrees.
// 1 2 3	7 2 1	 7 4 1
// 4 5 6 => 4 5 6 => 8 5 2
// 7 8 9    9 8 3    9 6 3
// rotate by layers
public class Main {

	public static int[][] switch1(int[][] a) {
		int [][] b = new int[a.length][a[0].length];
		for (int i=0; i<a.length; i++){
			b[i] = new int[a[0].length];
		}
		for (int i=0; i<a.length; i++){
			for (int j=0; j<a[0].length; j++){
				b[j][a.length-i-1]=a[i][j];
			}
		}
		return b;
	}
	
	public static int[][] switch2(int[][] a) {
		int layer = 0;
		for (layer = 0; layer < a.length/2+1; layer++){
			int t = a.length-1-layer*2;
			for (int i =0; i< t; i++){
				int ul,ur, dl, dr;
				ul = a[layer][layer+i];	//upper-left
				ur = a[layer+i][a.length-layer-1];		//upper-right
				dl = a[a.length-layer-1-i][layer];	//down-left
				dr = a[a.length-1-layer][a.length-layer-1-i];		//down-right
				a[layer][layer+i] = dl;
				a[layer+i][a.length-layer-1] = ul;
				a[a.length-layer-1-i][layer] = dr;
				a[a.length-1-layer][a.length-layer-1-i] = ur;
				
			}
		}
		return a;

	}
	public static void main(String[] args) {
		int [][] a = new int[3][3];
		for (int i=0; i<3; i++){
			a[i] = new int[3];
		}
		int count = 0;
		for (int i=0 ; i<3; i++){
			for (int j=0; j<3; j++){
				a[i][j] = count++;
			}
		}
		System.out.println(a.length);
		System.out.println(a[0].length);


		for (int i=0 ; i<3; i++){
			for (int j=0; j<3; j++){
				System.out.print(a[i][j]);
			}
			System.out.println();
		}
		int b[][]=switch2(a);
		for (int i=0 ; i<3; i++){
			for (int j=0; j<3; j++){
				System.out.print(b[i][j]);
			}
			System.out.println();
		}
		
		
	}

}
