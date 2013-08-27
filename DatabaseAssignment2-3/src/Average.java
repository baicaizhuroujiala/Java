/*
 * Class of average, to compute the average of some number
 */
public class Average {
	
	private double sum;
	private double num;
	
	Average(){
		sum = 0;
		num = 0;
	}
	
	//update the sum and num
	public void update(int quantity) {
		sum += quantity;
		num ++;
	}
	
	//get the average 
	public double getResult() {
		//divide sum and num
		return Math.divide(sum, num);	
	}
}
