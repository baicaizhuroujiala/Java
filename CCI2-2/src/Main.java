
public class Main {

	public static SingleNode nthToLast(SingleList s, int n){
		SingleNode n1 = s.getHead();
		SingleNode n2 = s.getHead();
		for(int i = 0 ; i < n ; i ++){
			n2 = n2.getNext();
		}
		while(n2 != s.getTail()){
			n2 = n2.getNext();
			n1 = n1.getNext();
		}
		return n1;
	}
	
	public static void main(String[] args) {
		SingleList s = new SingleList();
		s.add(1);
		s.add(2);
		s.add(3);
		s.add(4);
		s.add(5);
		s.add(6);
		s.add(7);
		s.add(8);	
		System.out.println(nthToLast(s, 3).getData());
	}

}
