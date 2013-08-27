
public class Main {

	public static void main(String[] args) {
		Stack s = new Stack();
		System.out.println(s.isEmpty());
		s.push(0);
		s.push(1);
		s.push(2);
		System.out.println(s.isEmpty());
		System.out.println(s.pop().data);
		System.out.println(s.pop().data);
		System.out.println(s.pop().data);
	}

}
