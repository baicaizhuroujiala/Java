//class of the row
public class RowOfAverage {
	public String customer;
	public String product;
	public int sumOfNY;
	public int numOfNY;
	public int sumOfNJ;
	public int numOfNJ;
	public int sumOfCT;
	public int numOfCT;
	
	//update one row
	//according state name update the sum of the quantity and the number of sales in corresponding state
	public void updateRow(String state, int quantity) {
		if (state.equals("NY")){
			sumOfNY += quantity;
			numOfNY++;
		}
		else if (state.equals("NJ")){
			sumOfNJ += quantity;
			numOfNJ++;
		}
		else if (state.equals("CT")){
			sumOfCT += quantity;
			numOfCT++;
		}
		/*
		 * There is PA in database
		else {
			System.out.println("ERROR");
		}
		*/
	}
	
	//construct method of one row
	RowOfAverage(String tempCustomer, String tempProduct, String state, int quantity){
		this.customer = tempCustomer;
		this.product = tempProduct;
		sumOfNY=0;
		numOfNY=0;
		sumOfNJ=0;
		numOfNJ=0;
		sumOfCT=0;
		numOfCT=0;
		this.updateRow(state, quantity);
	}
	
}

