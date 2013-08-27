
public class Main {
	
	public static void main(String[] args){
		Queue q = new Queue();
		q.enqueue(0);
		q.enqueue(1);
		q.enqueue(2);
		System.out.println(q.dequeue().data);
		System.out.println(q.dequeue().data);
		System.out.println(q.dequeue().data);

	}
	
}
