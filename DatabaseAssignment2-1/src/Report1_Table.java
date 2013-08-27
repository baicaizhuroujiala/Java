/*
 * Class of the table, has only one attribute, a list of row.
 */
import java.util.ArrayList;
import java.util.List;


public class Report1_Table {
	
	//list is a ArrayList of Report1_Row
	private List<Report1_Row> list = new ArrayList<Report1_Row>();

	//find the current row is in the list or not using customer, 
	//if there is, return a row number, if not, return -1
	public int getRow(String customer) {
		//loop the list to find the row which its customer name equals the argument
		for (int i=0 ; i<list.size() ; i++){
			if (list.get(i).getCustomer().equals(customer)){
				return i;
			}
		}
		return -1;
	}
	
	//update a row using row number, product, quantity
	public void updateRow(int row, String product, int quantity) {
		//invoke the update method of Report1_Row class, using product, quantity
		list.get(row).update(product, quantity);
	}

	//add a new row using customer, product and quantity
	public void addRow(String customer, String product, int quantity) {
		//create a object of Report1_Row by invoke the construct method. 
		Report1_Row row = new Report1_Row(customer, product, quantity);
		//add the object of Report1_Row to the list
		list.add(row);
	}

	//print the list
	public void display() {
		//print the lable
		System.out.println("CUSTOMER  DRINKS   FOODS    MISC");
		System.out.println("========  ======  ======  ======");
		for (int i=0 ; i<list.size() ; i++){
			System.out.format("%-8s  %6d  %6d  %6d%n", list.get(i).getCustomer(), list.get(i).getDrinks(), 
					list.get(i).getFoods(), list.get(i).getMisc());
		}
	}

}
