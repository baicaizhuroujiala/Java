/*
 * Instruction:
 * In local machine:
 * 1. cd ./DatabaseAssignment1-1/src					Enter the directory 
 * 2. scp *.java xluo3@postgres.cs.stevens.edu:			Upload all the java files
 * In server:
 * 1. mkdir DatabaseAssignment1-1						create directory
 * 2. mv *.java ./DatabaseAssignment1-1					move all java files to DatabaseAssighment1-1
 * 3. cd ./DatabaseAssignment1-1						enter DatabaseAssignment1-1 directory
 * 4. export CLASSPATH=/home/xluo3/postgresql-9.1-902.jdbc4.jar:.	export jdbc
 * 5. javac *.java										compile java files
 * 6. java sample										execute program
 * 
 * The data structure I choose to contain data is a list. 
 * I choose list because it is easy to use and compare to array, the length of list is not fixed. 
 * So it can handle all the situations no matter how many pairs of customer and product.
 * If using array, I have to give it a fixed length. 
 * If there are too many customer and product, the array may not sufficient. 
 * 
 * There are four files in the project:
 * 1. sample.java 
 * 		sample class used to get data from database using JDBC and create object from other class.
 * First, create a object of AverageOfSales class, to maintain a list.
 * Second, read data from the database 
 * Third, create a reference of one row of the list.
 * Fourth, find out the current row is in the list or not, if there is, return a row, if not, return null.
 * If the current row do exist in list, update the value of the row.
 * If the current row do not exist in list, so add a new row to the list.
 * At last, when finish reading all the data, display all rows in the list.
 * 
 * 2. AverageOfSales.java
 * 		Class of list, has only one attribute, a list of row.
 * getCurrentCustomerAndProduct used to find the current row is in the list or not, if there is, return a row, if not, return null.
 * addNewRowToList used to add a new row to the list.
 * displayAllRows used to display all rows in the list.
 * 
 * 3. RowOfAverage.java
 * 		Class of one row. Maintain data of one row.
 * updateRow used to update one row. According state name update the sum of the quantity and the number of sales in corresponding state
 * RowOfAverage is construct method of one row.
 * 
 * 4. Math.java
 * 		use to avoid divide by 0.
 */



//sample class used to get data from database using JDBC. 
import java.sql.*;

public class sample {
	
	public static void main(String[] args) 
	{	
		AverageOfSales averageOfSales = new AverageOfSales();
		//set up database's user name, password and URL

		String usr ="postgres";		//postgres
		String pwd ="881222";		//881222
		String url ="jdbc:postgresql://localhost:5432/postgres"; 
		//"jdbc:postgresql://155.246.89.29:5432/xluo3"
		//"jdbc:postgresql://localhost:5432/postgres"
		
		//load driver
		try 
		{
			Class.forName("org.postgresql.Driver");
			System.out.println("Success loading Driver!");
		} 

		catch(Exception e) 
		{
			System.out.println("Fail loading Driver!");
			e.printStackTrace();
		}
		
		//connecting server
		try 
		{
			Connection conn = DriverManager.getConnection(url, usr, pwd);
			System.out.println("Success connecting server!");
			
			//get query result to ResultSet rs
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Sales");

			while (rs.next()) 
			{
				//create a new row of the list
				RowOfAverage tempRow;
				String customer = rs.getString("cust");
				String product = rs.getString("prod");
				String state = rs.getString("state");
				int quantity = rs.getInt("quant");
				//find the current row is in the list or not, if there is, return a row, if not, return null
				tempRow = averageOfSales.getCurrentCustomerAndProduct(customer, product);
				if (tempRow != null){
					//the current row do exist in list, so update the value of the row
					tempRow.updateRow(state, quantity);
				}
				else{
					//the current row do not exist in list, so add a new row to the list
					averageOfSales.addNewRowToList(customer, product, state, quantity);
				}
//				System.out.println(rs.getString("cust") + " " + rs.getString("prod") + " " + rs.getString("day") + " " +
//					   	rs.getString("month") + " " + rs.getString("year") + " " + rs.getString("quant"));
			}
			//display all rows in the list
			averageOfSales.displayAllRows();
		} 

		catch(SQLException e) 
		{
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}

	}
	
		
			
	
}

	
	

	
	
	
	









