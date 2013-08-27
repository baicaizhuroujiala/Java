/*
 * Use to avoid divide by 0
 */
public class Math {
	public static int divide(int a, int b){
		if (b != 0){
			return a/b;
		}
		else {
			return 0;
		}
	}
	
	public static double divide(double a, double b){
		if (b != 0){
			return a/b;
		}
		else {
			return 0;
		}
	}
}
