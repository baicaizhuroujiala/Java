/*
 * Class of a sub row, used to maintain data of a sub row. It has a maximum, an average and two integer variable to record.
 * Report3_SubRow is a construct method.
 * update is used to update the row by different arguments.
 */
public class Report3_SubRow {
	private Maximum maximum;
	private Average average;
	private int before;		//record how many sales of the previous quarter between that quarter's average and maximum
	private int after;		//record how many sales of the following quarter between that quarter's average and maximum
	
	public Maximum getMaximum() {
		return maximum;
	}

	public Average getAverage() {
		return average;
	}

	public int getBefore() {
		return before;
	}

	public int getAfter() {
		return after;
	}

	//construct method
	public Report3_SubRow(){
		this.maximum = new Maximum();
		this.average = new Average();
		this.before = 0;
		this.after = 0;
	}

	//update by quantity, update maximum and average
	public void update(int quantity) {
		maximum.update(quantity);
		average.update(quantity);
	}

	//update by identifies and quantity,update before or after
	public void update(String temp, int quantity) {
		if (quantity >= this.average.getResult() && quantity <= this.getMaximum().getResult()){
			if (temp.equals("before")){
				before++;
			}
			else if (temp.equals("after")){
				after++;
			}
		}
	}
	
}
