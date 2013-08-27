public class Main{
	
	public static void Hanoi(SubStack start, SubStack end, SubStack goby, int n){
		if(n==1){
			int temp = start.pop().data;
			end.push(temp);
			System.out.println(start.ID + " -> " + end.ID);
		}
		else {
			Hanoi(start, goby, end, n-1);
			Hanoi(start, end, goby, 1);
			Hanoi(goby, end, start, n-1);
		}
		
	}
	public static void main(String[] args){
		SubStack a = new SubStack("A");
		SubStack b = new SubStack("B");
		SubStack c = new SubStack("C");
		int n = 3;
		int t = 3;
		while (n>0){
			a.push(n);
			n--;
		}
		Hanoi(a, c, b, 3);
		while (t>0){
			System.out.println(c.pop().data);
			t--;
		}
	}
}