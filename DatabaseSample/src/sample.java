//the count has a problem. If the first one is not PA, so the fist one is right, other 2 will count 1 more time. 
//If the fist one is PA, all 3 will count 1 more time.
import java.sql.*;
import java.util.ArrayList;

public class sample {
	static sample e=new sample();
	// class for creating record object
	class Record {
		private String cust;
		private String prod;
		//define variables for output1
		private int ny_count=1;//dividends cannot be zero
		private int ny_sum;
		private int nj_count=1;
		private int nj_sum;
		private int ct_count=1;
		private int ct_sum;
		//define variables for output2
		private int max_q;
		private int max_day;
		private int max_month;
		private int max_year;
		private String max_state;
		private int min_q;
		private int min_day;
		private int min_month;
		private int min_year;
		private String min_state;
		//define variables for output3
		private int sum;
		private int count;
		
		// generate getters and setters
		
		public int getSum() {
			return sum;
		}

		public void setSum(int sum) {
			this.sum = sum;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public int getMax_day() {
			return max_day;
		}

		public void setMax_day(int max_day) {
			this.max_day = max_day;
		}

		public int getMax_month() {
			return max_month;
		}

		public void setMax_month(int max_month) {
			this.max_month = max_month;
		}

		public int getMax_year() {
			return max_year;
		}

		public void setMax_year(int max_year) {
			this.max_year = max_year;
		}

		public String getMax_state() {
			return max_state;
		}

		public void setMax_state(String max_state) {
			this.max_state = max_state;
		}

		public int getMin_day() {
			return min_day;
		}

		public void setMin_day(int min_day) {
			this.min_day = min_day;
		}

		public int getMin_month() {
			return min_month;
		}

		public void setMin_month(int min_month) {
			this.min_month = min_month;
		}

		public int getMin_year() {
			return min_year;
		}

		public void setMin_year(int min_year) {
			this.min_year = min_year;
		}

		public String getMin_state() {
			return min_state;
		}

		public void setMin_state(String min_state) {
			this.min_state = min_state;
		}

		public int getMax_q() {
			return max_q;
		}

		public void setMax_q(int max_q) {
			this.max_q = max_q;
		}

		public int getMin_q() {
			return min_q;
		}

		public void setMin_q(int min_q) {
			this.min_q = min_q;
		}

		public int getNy_count() {
			return ny_count;
		}

		public void setNy_count(int ny_count) {
			this.ny_count = ny_count;
		}

		public int getNy_sum() {
			return ny_sum;
		}

		public void setNy_sum(int ny_sum) {
			this.ny_sum = ny_sum;
		}

		public int getNj_count() {
			return nj_count;
		}

		public void setNj_count(int nj_count) {
			this.nj_count = nj_count;
		}

		public int getNj_sum() {
			return nj_sum;
		}

		public void setNj_sum(int nj_sum) {
			this.nj_sum = nj_sum;
		}

		public int getCt_count() {
			return ct_count;
		}

		public void setCt_count(int ct_count) {
			this.ct_count = ct_count;
		}

		public int getCt_sum() {
			return ct_sum;
		}

		public void setCt_sum(int ct_sum) {
			this.ct_sum = ct_sum;
		}

		public String getCust() {
			return cust;
		}

		public void setCust(String cust) {
			this.cust = cust;
		}

		public String getProd() {
			return prod;
		}

		public void setProd(String prod) {
			this.prod = prod;
		}

	}

	//********************************************************************

	// main
	public static void main(String[] args) 
		{
			//test connection
			String usr ="postgres";
			String pwd ="881222";
			String url ="jdbc:postgresql://localhost:5432/postgres";
			
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

			try 
			{
				Connection conn = DriverManager.getConnection(url, usr, pwd);
				System.out.println("Success connecting server!");

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sales");
				//define new list to store captured info
				ArrayList<Record> list = new ArrayList<Record>();
				
				while (rs.next()) {
					boolean addFlag=true;//use to judge if a record need to be stored
					
					if(list.size()==0){//it's the first record
						//common part data
						Record record = e.new Record();
						record.setCust(rs.getString("cust"));
						record.setProd(rs.getString("prod"));
						//store data for output1
						if("NY".equals(rs.getString("state"))){
							record.setNy_count(1);
							record.setNy_sum(rs.getInt("quant"));
						}else if("NJ".equals(rs.getString("state"))){
							record.setNj_count(1);
							record.setNj_sum(rs.getInt("quant"));
						}else if("CT".equals(rs.getString("state"))){
							record.setCt_count(1);
							record.setCt_sum(rs.getInt("quant"));
						}
						//store data for output2
						record.setMax_q(rs.getInt("quant"));
						record.setMax_state(rs.getString("state"));
						record.setMax_day(rs.getInt("day"));
						record.setMax_month(rs.getInt("month"));
						record.setMax_year(rs.getInt("year"));
						record.setMin_q(rs.getInt("quant"));
						record.setMin_state(rs.getString("state"));
						record.setMin_day(rs.getInt("day"));
						record.setMin_month(rs.getInt("month"));
						record.setMin_year(rs.getInt("year"));
						//store data for output3
						record.setCount(1);
						record.setSum(rs.getInt("quant"));
						
						list.add(record);
						
					}else{//from the second record
						
						for(int i=0;i<list.size();i=i+1){//check from the existed list if this record already in
							if(list.get(i).getCust().equals(rs.getString("cust"))&&
									list.get(i).getProd().equals(rs.getString("prod"))){//same combination,store data
								//store data for output1
								if("NY".equals(rs.getString("state"))){//judge diff states
									int temp_count=list.get(i).getNy_count();
									list.get(i).setNy_count(temp_count+1);
									int temp_sum=list.get(i).getNy_sum()+rs.getInt("quant");
									list.get(i).setNy_sum(temp_sum);
								}else if("NJ".equals(rs.getString("state"))){
									int temp_count=list.get(i).getNj_count();
									list.get(i).setNj_count(temp_count+1);
									int temp_sum=list.get(i).getNj_sum()+rs.getInt("quant");
									list.get(i).setNj_sum(temp_sum);
								}else if("CT".equals(rs.getString("state"))){
									int temp_count=list.get(i).getCt_count();
									list.get(i).setCt_count(temp_count+1);
									int temp_sum=list.get(i).getCt_sum()+rs.getInt("quant");
									list.get(i).setCt_sum(temp_sum);
								}
								//store data for output2
								if(rs.getInt("quant")>=list.get(i).getMax_q()){//judge if the current data is a max quant
									list.get(i).setMax_q(rs.getInt("quant"));
									list.get(i).setMax_state(rs.getString("state"));
									list.get(i).setMax_day(rs.getInt("day"));
									list.get(i).setMax_month(rs.getInt("month"));
									list.get(i).setMax_year(rs.getInt("year"));
									
								}else if(rs.getInt("quant")<list.get(i).getMin_q()){//judge if the current data is a min quant
									list.get(i).setMin_q(rs.getInt("quant"));
									list.get(i).setMin_state(rs.getString("state"));
									list.get(i).setMin_day(rs.getInt("day"));
									list.get(i).setMin_month(rs.getInt("month"));
									list.get(i).setMin_year(rs.getInt("year"));
								}
								//store data for output3
								int t_count=list.get(i).getCount();
								list.get(i).setCount(t_count+1);
								int t_sum=list.get(i).getSum()+rs.getInt("quant");
								list.get(i).setSum(t_sum);

								addFlag=false;//already existed,then don't need to create new record
								break;	//once find a same combination,jump out of loop
							}		
						}
						if(addFlag==true){//new combination,create new record
							//common part data
							Record record = e.new Record();
							record.setCust(rs.getString("cust"));
							record.setProd(rs.getString("prod"));
							//store data for output1
							if("NY".equals(rs.getString("state"))){
								record.setNy_count(1);
								record.setNy_sum(rs.getInt("quant"));
							}else if("NJ".equals(rs.getString("state"))){
								record.setNj_count(1);
								record.setNj_sum(rs.getInt("quant"));
							}else if("CT".equals(rs.getString("state"))){
								record.setCt_count(1);
								record.setCt_sum(rs.getInt("quant"));
							}
							//store data for output2
							record.setMax_q(rs.getInt("quant"));
							record.setMax_state(rs.getString("state"));
							record.setMax_day(rs.getInt("day"));
							record.setMax_month(rs.getInt("month"));
							record.setMax_year(rs.getInt("year"));
							record.setMin_q(rs.getInt("quant"));
							record.setMin_state(rs.getString("state"));
							record.setMin_day(rs.getInt("day"));
							record.setMin_month(rs.getInt("month"));
							record.setMin_year(rs.getInt("year"));
							//store data for output3
							record.setCount(1);
							record.setSum(rs.getInt("quant"));
							
							list.add(record);
						}
					}
				}
				
			//output report1
			System.out.println("CUSTOMER   PRODUCT   NY_AVG    NJ_AVG   CT_AVG");
			System.out.println("========   =======   ======    ======   ======");
			for(int j=0;j<list.size();j++)
			{
				System.out.println(list.get(j).getCust() + " " + list.get(j).getProd()+ " "+
							list.get(j).getNy_sum()/list.get(j).getNy_count()+" "+
						list.get(j).getNj_sum()/list.get(j).getNj_count()+" "+
							list.get(j).getCt_sum()/list.get(j).getCt_count());	
			}
			System.out.println("****************************************************************************");
			//output report2&3
			System.out.println("CUSTOMER   PRODUCT   MAX_Q    DATA       STATE     MIN_Q   DATA        STATE     AVG_Q");
			System.out.println("========   =======   ======   ========   =====     ======  =========   =====     ========");
			for(int k=0;k<list.size();k++)
			{
				System.out.println(list.get(k).getCust() + " " + list.get(k).getProd()+ " "+
							list.get(k).getMax_q()+" "+
						list.get(k).getMax_month()+"/"+list.get(k).getMax_day()+"/"+list.get(k).getMax_year()+"  "
							+list.get(k).getMax_state()+"  "
						+list.get(k).getMin_q()+"  "+
							list.get(k).getMin_month()+"/"+list.get(k).getMin_day()+"/"+list.get(k).getMin_year()+"  "
							+list.get(k).getMin_state()+"  "
							+list.get(k).getSum()/list.get(k).getCount());	
			}
			
			}catch(SQLException e) 
			{
				System.out.println("Connection URL or username or password errors!");
				e.printStackTrace();
			}

		}
}
