/*
 * Class of the table, has only one attribute, a list of row.
 * getRow used to find the current row is in the table or not using customer, if there is, return a row number, if not, return -1
 * addRow used to add a new row to the table.
 * display used to display all rows in the table. 
 */
import java.util.ArrayList;
import java.util.List;


public class Report2_Table {
	
	//list is a ArrayList of Report2_Row
	private List<Report2_Row> list = new ArrayList<Report2_Row>(); 
	//n is used for display "<NULL>"
	private static final String n = "<NULL>";
	
	//find the current row is in the list or not using customer, 
	//if there is, return a row number, if not, return -1
	public int getRow(String customer, String product) {
		for (int i=0; i<list.size(); i++){
			if (list.get(i).getCustomer().equals(customer) && list.get(i).getProduct().equals(product)){
				return i;
			}
		}
		return -1;
	}

	//update a row using row number, month, quantity
	public void updateRow(int row, String month, int quantity) {
		//invoke the update method of Report2_Row class, using month, quantity
		list.get(row).update(month, quantity);
	}

	//add a new row using customer, product and quantity
	public void addRow(String customer, String product, String month, int quantity) {
		//create a object of Report2_Row by invoke the construct method.
		Report2_Row row = new Report2_Row(customer, product, month, quantity);
		//add the object of Report2_Row to the list
		list.add(row);
	}

	//print the list
	public void display() {
		System.out.println("CUSTOMER PRODUCT  QUARTER  BEFORE_AVG  AFTER_AVG");
		System.out.println("======== =======  =======  ==========  =========");
		for (int i=0; i<list.size(); i++){
			System.out.format("%-8s %-7s  %7s  %10s  %9s%n", list.get(i).getCustomer(),
					list.get(i).getProduct(), "Q1", n, Integer.toString(list.get(i).getQ2().getResult()));
			System.out.format("%-8s %-7s  %7s  %10s  %9s%n", list.get(i).getCustomer(),
					list.get(i).getProduct(), "Q2", Integer.toString(list.get(i).getQ1().getResult()), Integer.toString(list.get(i).getQ3().getResult()));
			System.out.format("%-8s %-7s  %7s  %10s  %9s%n", list.get(i).getCustomer(),
					list.get(i).getProduct(), "Q3", Integer.toString(list.get(i).getQ2().getResult()), Integer.toString(list.get(i).getQ4().getResult()));
			System.out.format("%-8s %-7s  %7s  %10s  %9s%n", list.get(i).getCustomer(),
					list.get(i).getProduct(), "Q4", Integer.toString(list.get(i).getQ3().getResult()), n);
		}
	}

}
