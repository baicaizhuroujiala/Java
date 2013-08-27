/*
 * Class of one row. Maintain data of one row.
 * update used to update one row. According product's name update the sum of the quantity.
 * Report2_Row is construct method of one row.
 */
public class Report2_Row {
	private String customer;	
	private String product;
	private Average Q1;		//average of quarter 1
	private Average Q2;		//average of quarter 2
	private Average Q3;		//average of quarter 3
	private Average Q4;		//average of quarter 4
	
	//construct method
	public Report2_Row(String tempCustomer, String tempProduct, String month, int quantity) {
		this.customer = tempCustomer;
		this.product = tempProduct;
		Q1 = new Average();
		Q2 = new Average();
		Q3 = new Average();
		Q4 = new Average();
		//update the row
		this.update(month, quantity);
	}
	//access method
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Average getQ1() {
		return Q1;
	}
	public void setQ1(Average q1) {
		Q1 = q1;
	}
	public Average getQ2() {
		return Q2;
	}
	public void setQ2(Average q2) {
		Q2 = q2;
	}
	public Average getQ3() {
		return Q3;
	}
	public void setQ3(Average q3) {
		Q3 = q3;
	}
	public Average getQ4() {
		return Q4;
	}
	public void setQ4(Average q4) {
		Q4 = q4;
	}
	
	//update a row depending on the month
	public void update(String month, int quantity) {
		int m = Integer.parseInt(month);
		if (m==1 || m==2 || m==3){
			Q1.update(quantity);
		}
		else if (m==4 || m==5 || m==6){
			Q2.update(quantity);
		}
		else if (m==7 || m==8 || m==9){
			Q3.update(quantity);
		}
		else if (m==10 || m==11 || m==12){
			Q4.update(quantity);
		}
	}
	
	
}
