public class Main{
	
	public static SingleNode FindBeginning(SingleList s){
		SingleNode a = s.getHead();
		SingleNode b = s.getHead();
		a = a.getNext().getNext();
		b= b.getNext();
		while (a != b) {
			a = a.getNext().getNext();
			b= b.getNext();
		}
		a = s.getHead();
		while (a != b) {
			a = a.getNext();
			b = b.getNext();
		}
		return a;
	}	

	public static void main(String[] args){
		SingleList s = new SingleList();
		s.add(1);
		s.add(2);
		s.add(3);
		s.add(4);
		SingleNode t = s.getTail();
		s.add(5);
		s.add(6);
		s.add(7);
		s.add(8);
		s.add(9);
		s.add(10);
		s.add(11);
		s.getTail().setNext(t);
		System.out.println(FindBeginning(s).getData());
		
	}
}