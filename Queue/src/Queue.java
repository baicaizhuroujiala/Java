public class Queue{
	public QueueNode first, last;

	public Queue(){
		first = null;
		last = null;
	}

	public void enqueue(QueueNode q){
		if (last != null){
			last.next = q;
			last = q;
		}
		else {
			last = q;
			first = q;
		}
	}
	
	public void enqueue(int i){
		QueueNode q = new QueueNode(i);
		enqueue(q);
		
		
	}
	public QueueNode dequeue(){
		if (first != null){
			QueueNode q = first;
			first = q.next;
			return q;
		}
		else return null;
	}

}