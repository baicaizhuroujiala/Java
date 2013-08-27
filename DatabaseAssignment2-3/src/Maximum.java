/*
 * Class of Maximum, to compute the maximum of some number
 * update used to update the max
 * getResult used to get the max 
 */
public class Maximum {
	private int max;
	
	public Maximum(){
		max = 0;
	}
	
	//update the max
	public void update(int t){
		if (t>max){
			max = t;
		}
	}
	
	//get the max
	public int getResult(){
		return max;
	}
}
