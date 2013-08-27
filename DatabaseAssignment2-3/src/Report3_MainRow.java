/*
 * Class of one main row. Maintain data of one row. Contain customer, product and 4 sub rows. 
 * update used to update one row. According product's name update the sum of the quantity.
 * Report3_Row is construct method of one row.
 */
public class Report3_MainRow {
	private String customer;
	private String product;
	private Report3_SubRow Q1;		//the sub row of quarter 1
	private Report3_SubRow Q2;		//the sub row of quarter 2
	private Report3_SubRow Q3;		//the sub row of quarter 3
	private Report3_SubRow Q4;		//the sub row of quarter 4
	
	//construct method
	public Report3_MainRow(String tempCustomer, String tempProduct, String month, int quantity) {
		this.customer = tempCustomer;
		this.product = tempProduct;
		this.Q1 = new Report3_SubRow();
		this.Q2 = new Report3_SubRow();
		this.Q3 = new Report3_SubRow();
		this.Q4 = new Report3_SubRow();
		this.update(1, month, quantity);
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
	public Report3_SubRow getQ1() {
		return Q1;
	}
	public void setQ1(Report3_SubRow q1) {
		Q1 = q1;
	}
	public Report3_SubRow getQ2() {
		return Q2;
	}
	public void setQ2(Report3_SubRow q2) {
		Q2 = q2;
	}
	public Report3_SubRow getQ3() {
		return Q3;
	}
	public void setQ3(Report3_SubRow q3) {
		Q3 = q3;
	}
	public Report3_SubRow getQ4() {
		return Q4;
	}
	public void setQ4(Report3_SubRow q4) {
		Q4 = q4;
	}
	
	//update one row according the loop time, month, quantity
	public void update(int time, String month, int quantity) {
		int m = Integer.parseInt(month);
		//in first loop, invoke update method in sub row by quantity
		if (time == 1) {
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
		//in second loop, invoke update method in sub row by before or after identifier and quantity
		else if (time == 2) {
			if (m==1 || m==2 || m==3){
				Q2.update("before", quantity);
			}
			else if (m==4 || m==5 || m==6){
				Q1.update("after", quantity);
				Q3.update("before", quantity);
			}
			else if (m==7 || m==8 || m==9){
				Q2.update("after", quantity);
				Q4.update("before", quantity);
			}
			else if (m==10 || m==11 || m==12){
				Q3.update("after", quantity);
			}
		}
		
	}

	
}
