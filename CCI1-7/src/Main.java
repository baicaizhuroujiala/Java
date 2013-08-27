//Write an algorithm such that if an element in an MxN matrix is 0, its entire row and column is set to 0.
public class Main {

	public static int[][] setZero(int[][] a){
		int row = a.length;
		int column = a[0].length;
		int label[] = new int[row];
		for (int i=0; i<row; i++){
			System.out.println(label[i]);
		}
		for (int i=0; i<row; i++){
			for (int j=0; j<column; j++){
				if (a[i][j] == 0) {
					label[i] = j+1;
				}
			}
		}
		for (int i=0; i<row; i++){
			if(label[i]!=0){
				for (int j=0; j<column; j++){
					a[i][j] = 0;
				}
				for (int j=0; j<row; j++){
					a[j][label[i]-1] = 0;
				}
			}
		}
		return a;
	}
	
	public static void main(String[] args) {
		int a[][] = {{1,2,3,4},{5,0,7,8},{1,9,1,2}};
		int b[][] = setZero(a);
		for(int i=0; i<b.length; i++){
			for(int j=0; j<b[0].length; j++){
				System.out.print(b[i][j]);
			}
			System.out.println();
		}
	}

}
