public class Stack {
	public StackNode top;

	public Stack(){
		top = null;
	}

	public boolean isEmpty(){
		return top == null;
	}

	public void push(StackNode s){
		s.next = top;
		top = s;
	}

	public void push(int i){
		StackNode s = new StackNode(i);
		s.next = top;
		top = s;
	}
	
	public StackNode pop(){
		if(top != null){
			StackNode s = top;
			top = s.next;
			return s;
		}
		else {
			return null;
		}
	}
	
	
	public int popData(){
		if(top != null){
			StackNode s = top;
			top = s.next;
			return s.data;
		}
		else {
			return -1;
		}
	}
	
	public int peek(){
		if(top != null){
			return top.data;
		}
		else {
			return -1;
		}
	}
}