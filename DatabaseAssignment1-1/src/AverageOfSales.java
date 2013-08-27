//class of the list
import java.util.ArrayList;
import java.util.List;


public class AverageOfSales {
	//only one attribute, a list of row
	public List<RowOfAverage> tempData = new ArrayList<RowOfAverage>();

	//find the current row is in the list or not, if there is, return a row, if not, return null
	public RowOfAverage getCurrentCustomerAndProduct(String customer,
			String product) {
		for (int i=0; i < tempData.size(); i++){
			if (tempData.get(i).customer.equals(customer) && 
					tempData.get(i).product.equals(product)){
				return tempData.get(i);
			}
		}
		return null;
	}

	//add a new row to the list
	public void addNewRowToList(String customer, String product, String state, int quantity) {
		RowOfAverage tempRow = new RowOfAverage(customer, product, state, quantity);
		tempData.add(tempRow);
	}

	//display all rows in the list
	public void displayAllRows() {
		System.out.println("CUSTOMER  PRODUCT  NY_AVG  NJ_AVG  CT_AVG");
		System.out.println("========  =======  ======  ======  ======");
		for (int i=0; i < tempData.size(); i++){
			System.out.format("%-8s  %-7s  %6d  %6d  %6d%n", 
								tempData.get(i).customer, 
								tempData.get(i).product,
								Math.divide(tempData.get(i).sumOfNY,tempData.get(i).numOfNY),
								Math.divide(tempData.get(i).sumOfNJ,tempData.get(i).numOfNJ),
								Math.divide(tempData.get(i).sumOfCT,tempData.get(i).numOfCT));
			/*
			System.out.println(tempData.get(i).customer + " " + tempData.get(i).product + " "
								+ Integer.toString(Math.divide(tempData.get(i).sumOfNY,tempData.get(i).numOfNY)) + " " 
								+ Integer.toString(Math.divide(tempData.get(i).sumOfNJ,tempData.get(i).numOfNJ)) + " " 
								+ Integer.toString(Math.divide(tempData.get(i).sumOfCT,tempData.get(i).numOfCT)) );
			*/
		}
	}

}
