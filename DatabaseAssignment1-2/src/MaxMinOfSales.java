import java.util.ArrayList;
import java.util.List;


public class MaxMinOfSales {
	//only one attribute, a list of row
	public List<RowOfMaxMin> tempData = new ArrayList<RowOfMaxMin>();

	//find the current row is in the list or not, if there is, return a row, if not, return null
	public RowOfMaxMin getCurrentCustomerAndProduct(String customer,
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
	public void addNewRowToList(String customer, String product, String state,
			int quantity, String date) {
		RowOfMaxMin tempRow = new RowOfMaxMin(customer, product, state, quantity, date);
		tempData.add(tempRow);
	}

	//display all rows in the list
	public void displayAllRows() {
		System.out.println("CUSTOMER  PRODUCT  MAX_Q  DATE        STATE  MIN_Q  DATE        STATE  AVG_Q");
		System.out.println("========  =======  =====  ==========  =====  =====  ==========  =====  =====");
		for (int i=0; i < tempData.size(); i++){
	
			System.out.format("%-8s  %-7s  %5d  %10s  %5s  %5d  %10s  %5s  %5d%n" ,
								tempData.get(i).customer,
								tempData.get(i).product,
								tempData.get(i).max,
								FormatString.formateDateString(tempData.get(i).maxDate),
								tempData.get(i).maxState,
								tempData.get(i).min,
								FormatString.formateDateString(tempData.get(i).minDate),
								tempData.get(i).minState,
								Math.divide(tempData.get(i).sum,tempData.get(i).num));
			/*
			System.out.println(
							tempData.get(i).customer + " " 
							+ tempData.get(i).product + " "
							+ Integer.toString(tempData.get(i).max) + " " 
							+ tempData.get(i).maxDate + " "
							+ tempData.get(i).maxState + " "
							+ Integer.toString(tempData.get(i).min) + " " 
							+ tempData.get(i).minDate + " "
							+ tempData.get(i).minState + " "
							+ Integer.toString(Math.divide(tempData.get(i).sum,tempData.get(i).num)) 
							);
		*/
		}
	}

}
