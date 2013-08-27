
public class Main {

	public static void main(String[] args) {
		SingleList s = new SingleList();
		System.out.println(s.isEmpty());
		s.add(1);
		s.add(2);
		s.add(3);
		s.add(4);
		s.add(5);
//		System.out.println(s.isEmpty());
//		s.traversal();
//		System.out.println(s.length());
//		System.out.println(s.find(5));
//		System.out.println(s.find(6));
		s.delete(1);
		s.delete(5);
		s.delete(3);
		s.traversal();
		s.insert(5, s.find(4));
		s.insert(3, s.find(2));
		s.traversal();
		s.insertHead(1);
		s.traversal();

	}

}
