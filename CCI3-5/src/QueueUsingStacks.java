public class QueueUsingStacks{
	
	Stack i = new Stack();
	Stack o = new Stack();
	
	public void enqueue(int t){
		i.push(t);
	}
	
	public int dequeue(){
		if (o.isEmpty()){
			while(!i.isEmpty()){
				o.push(i.pop().data);
			}
		}
		return o.pop().data;
	}

	
	public static void main(String[] args){
		QueueUsingStacks qs = new QueueUsingStacks();
		qs.enqueue(1);
		qs.enqueue(2);
		qs.enqueue(3);
		System.out.println(qs.dequeue());
		System.out.println(qs.dequeue());
		qs.enqueue(4);
		qs.enqueue(5);
		System.out.println(qs.dequeue());
		System.out.println(qs.dequeue());	
		System.out.println(qs.dequeue());	
	}
}