
public class RowOfMaxMin {
	public String customer;
	public String product;
	public int max;
	public String maxDate;
	public String maxState;
	public int min;
	public String minDate;
	public String minState;
	public int sum;
	public int num;
	
	//construct method of one row
	public RowOfMaxMin(String tempCustomer, String tempProduct, String state,
			int quantity, String date) {
		this.customer = tempCustomer;
		this.product = tempProduct;
		max = quantity;
		maxDate = date;
		maxState = state;
		min = quantity;			//min init with quantity
		minDate = date;
		minState = state;
		sum = 0;
		num = 0;
		this.updateRow(state, quantity, date);
	}
	
	//update one row, choose max and min
	public void updateRow(String state, int quantity, String date) {
		sum += quantity;
		num++;
		if (quantity > max){
			max = quantity;
			maxDate = date;
			maxState = state;
		}
		if (quantity < min){
			min = quantity;
			minDate = date;
			minState = state;
		}
	}
}
