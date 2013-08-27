public class AscendingStack{

	Stack s = new Stack();
	Stack temp = new Stack();

	public void push(int i){
		if(s.isEmpty()){
			s.push(i);
		}		
		else {
			while(s.peek()<i){
				temp.push(s.pop().data);
			}
			s.push(i);
			while(!temp.isEmpty()){
				s.push(temp.pop().data);
			}
		}
	}

	public int pop(){
		return s.pop().data;
	}	

	public static void main(String[] args){
		AscendingStack as = new AscendingStack();
		as.push(4);
		as.push(1);
		as.push(3);
		as.push(2);
		System.out.println(as.pop());
		System.out.println(as.pop());
		System.out.println(as.pop());
		System.out.println(as.pop());
	}
}