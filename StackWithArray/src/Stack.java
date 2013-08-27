
public class Stack {
	int top;
	static int len = 3;
	int[] s = new int[len];
	public Stack(){
		top = -1;
	}
	
	public boolean push(int i){
		if(top<len-1){
			s[++top] = i;
			return true;
		}
		else return false;
	}
	
	public int pop(){
		if(top >= 0){
			return s[top--];
		}
		else {
			return -1;
		}
	}
}
