import java.util.ArrayList;


public class Routine_Info {
	String name;
	String image;
	long start;
	long tail;
	long count;
	ArrayList<Long> returnValue;
	ArrayList<Long> returnAddress;
	
	Routine_Info(){
		returnValue = new ArrayList<Long>();
		returnAddress = new ArrayList<Long>();
	}
	
	public void print(){
		System.out.println("Name \t" + name);
		System.out.println("Image \t" + image);
		System.out.println("Start \t" + Long.toHexString(start));
		System.out.println("Tail \t" + Long.toHexString(tail));
		System.out.println("Count \t" + Long.toHexString(count));
		for(int i = 0; i < count; i++){
			System.out.println("Return Value \t" + Long.toHexString(returnValue.get(i)));
			System.out.println("Return Address \t" + Long.toHexString(returnAddress.get(i)));
		}
		System.out.println();
	}
}
