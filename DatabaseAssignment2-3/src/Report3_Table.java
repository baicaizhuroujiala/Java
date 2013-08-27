/*
 * Class of the table, has only one attribute, a list of row.
 * getRow used to find the current row is in the table or not using customer, if there is, return a row number, if not, return -1
 * addRow used to add a new row to the table.
 * display used to display all rows in the table. 
 */
import java.util.ArrayList;
import java.util.List;


public class Report3_Table {

	//list is a ArrayList of Report2_Row
	private List<Report3_MainRow> list = new ArrayList<Report3_MainRow>();
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
	
	//update a row using loop time, row number, month, quantity
	public void updateRow(int time, int row, String month, int quantity) {
		//invoke the update method of Report3_MainRow class, using loop time, month, quantity
		list.get(row).update(time, month, quantity);		
	}

	//add a new row using customer, product, month and quantity
	public void addRow(String customer, String product, String month, int quantity) {
		//create a object of Report3_MainRow by invoke the construct method.
		Report3_MainRow row = new Report3_MainRow(customer, product, month, quantity);
		//add the object of Report3_MainRow to the list
		list.add(row);
	}

	//display the list
	public void display() {
		System.out.println("CUSTOMER PRODUCT  QUARTER  BEFORE_TOT  AFTER_TOT");
		System.out.println("======== =======  =======  ==========  =========");
		for (int i=0; i<list.size(); i++){
			System.out.format("%-8s %-7s  %7s  %10s  %9s%n", list.get(i).getCustomer(), list.get(i).getProduct(),
					"Q1", n, Integer.toString(list.get(i).getQ1().getAfter()));
			System.out.format("%-8s %-7s  %7s  %10s  %9s%n", list.get(i).getCustomer(), list.get(i).getProduct(),
					"Q2", Integer.toString(list.get(i).getQ2().getBefore()), Integer.toString(list.get(i).getQ2().getAfter()));
			System.out.format("%-8s %-7s  %7s  %10s  %9s%n", list.get(i).getCustomer(), list.get(i).getProduct(),
					"Q3", Integer.toString(list.get(i).getQ3().getBefore()), Integer.toString(list.get(i).getQ3().getAfter()));
			System.out.format("%-8s %-7s  %7s  %10s  %9s%n", list.get(i).getCustomer(), list.get(i).getProduct(),
					"Q4", Integer.toString(list.get(i).getQ4().getBefore()), n);
			
//			System.out.println(list.get(i).getCustomer()+" "+list.get(i).getProduct()+" "
//					+list.get(i).getQ1().getMaximum().getResult()+" "+list.get(i).getQ1().getAverage().getResult()+" "
//					+list.get(i).getQ1().getBefore()+" "+list.get(i).getQ1().getAfter()+" "
//					+list.get(i).getQ2().getMaximum().getResult()+" "+list.get(i).getQ2().getAverage().getResult()+" "
//					+list.get(i).getQ2().getBefore()+" "+list.get(i).getQ2().getAfter()+" "
//					+list.get(i).getQ3().getMaximum().getResult()+" "+list.get(i).getQ3().getAverage().getResult()+" "
//					+list.get(i).getQ3().getBefore()+" "+list.get(i).getQ3().getAfter()+" "
//					+list.get(i).getQ4().getMaximum().getResult()+" "+list.get(i).getQ4().getAverage().getResult()+" "
//					+list.get(i).getQ4().getBefore()+" "+list.get(i).getQ4().getAfter()+" "
//					);
		}
	}

}
