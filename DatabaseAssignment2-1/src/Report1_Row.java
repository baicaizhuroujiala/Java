/*
 * Class of a single row in the table
 */
public class Report1_Row {
	private String customer;	//customer name
	private int drinks;			//quantity of drinks
	private int foods;			//quantity of foods
	private int misc;			//quantity of misc
	
	//construct method
	public Report1_Row(String tempCustomer, String product, int quantity) {
		//initialize
		this.customer = tempCustomer;
		drinks = 0;
		foods = 0;
		misc = 0;
		//update the row
		this.update(product, quantity);
	}
	//access method
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public int getDrinks() {
		return drinks;
	}
	public void setDrinks(int drinks) {
		this.drinks = drinks;
	}
	public int getFoods() {
		return foods;
	}
	public void setFoods(int foods) {
		this.foods = foods;
	}
	public int getMisc() {
		return misc;
	}
	public void setMisc(int misc) {
		this.misc = misc;
	}
	
	//update a row depending on the product name.
	public void update(String product, int quantity) {
		if (product.equals("Milk") || product.equals("Pepsi") || product.equals("Coke")){
			drinks += quantity;
		}
		else if (product.equals("Bread") || product.equals("Eggs") || product.equals("Fruits") || product.equals("Butter") 
				|| product.equals("Cookies") || product.equals("Yogurt")){
			foods += quantity;
		}
		else if (product.equals("Soap")){
			misc += quantity;
		}
	}
	
	
}
