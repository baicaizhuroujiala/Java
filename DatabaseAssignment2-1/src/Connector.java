/*
 * Instruction:
 * In local machine:
 * 1. cd ./DatabaseAssignment2-1/src					Enter the directory 
 * 2. scp *.java xluo3@postgres.cs.stevens.edu:			Upload all the java files
 * In server:
 * 1. mkdir DatabaseAssignment2-1						create directory
 * 2. mv *.java ./DatabaseAssignment2-1					move all java files to DatabaseAssighment1-1
 * 3. cd ./DatabaseAssignment2-1						enter DatabaseAssignment1-1 directory
 * 4. export CLASSPATH=/home/xluo3/postgresql-9.1-902.jdbc4.jar:.	export jdbc
 * 5. javac *.java										compile java files
 * 6. java Connector									execute program
 * 
 * There are 3 files in the project:
 * 1. Connector.java 
 * 		Connector class used to get data from database using JDBC and create object from other class.
 * 		1. Create a object of Report1_Table class, to maintain a list of Report1_Row object.
 * 		2. Read data from the database. 
 * 		3. Create a integer to record a row number.
 * 		4. Find out the current row is in the table or not, if there is, return a row, if not, return null.
 * 	 	   If the current row do exist in table, update the value of the row.
 *		   If the current row do not exist in table, so add a new row to the table.
 * 		5. When finish reading all the data, display all rows in the table.
 * 
 * 2. Report1_Table.java
 * 		Class of the table, has only one attribute, a list of row.
 * 		getRow used to find the current row is in the table or not using customer, if there is, return a row number, if not, return -1
 * 		addRow used to add a new row to the table.
 * 		display used to display all rows in the table.
 * 
 * 3. Report1_Row.java
 * 		Class of one row. Maintain data of one row.
 * 		updateRow used to update one row. According product's name update the sum of the quantity.
 *		 Report1_Row is construct method of one row.
 */
import java.sql.*;
public class Connector {

	public static void main(String[] args) 
	{
		//create a reference of the object of Report1_Table class, to maintain a list of Report1_Row object.
		Report1_Table table = new Report1_Table();
		//set up database's user name, password and URL
//		String usr ="postgres";
//		String pwd ="881222";
//		String url ="jdbc:postgresql://localhost:5432/postgres";
		String usr ="xluo3";
		String pwd ="xluo3";
		String url ="jdbc:postgresql://155.246.89.29:5432/xluo3";
		
		//load Driver
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
		
		//create connection to server
		try 
		{
			//get connection
			Connection conn = DriverManager.getConnection(url, usr, pwd);
			System.out.println("Success connecting server!");
			
			//put the result of query - "select * from Sales" - into ResultSet rs
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Sales");

			while (rs.next()) 	//rs still have next element
			{
				String customer = rs.getString("cust");		//get customer from rs
				String product = rs.getString("prod");		//get product from rs
				int quantity = rs.getInt("quant");			//get quantity from rs
				int row;		//temporary variable, to record which row get from table
				//find the current row is in the table or not using customer name, 
				//if there is, return a row's number, if not, return -1
				row = table.getRow(customer); 
				//can find a row in the table, update the currently row
				if (row != -1) {
					//update a row using row number, product, quantity
					table.updateRow(row, product, quantity);
				}
				//can not find a row in the table, so add a new row
				else {
					//add a new row to the table using customer, product, quantity
					table.addRow(customer, product, quantity);
				}
				
//				System.out.println(rs.getString("cust") + " " + rs.getString("prod") + " " + rs.getString("day") + " " +
//					   	rs.getString("month") + " " + rs.getString("year") + " " + rs.getString("quant"));
			}
			//display the table
			table.display();
		} 

		//handle SQLException
		catch(SQLException e) 
		{
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}

	}

}
