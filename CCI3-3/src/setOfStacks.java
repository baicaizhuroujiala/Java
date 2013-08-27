import java.util.ArrayList;

public class setOfStacks{
	
	ArrayList<Stack> list = new ArrayList<Stack>();
	int current = -1;
	
	public void push(int i){
		if (current == -1){
			list.add(new Stack());
			current ++;
		}
		if(!list.get(current).push(i)){
			list.add(new Stack());
			current ++;
			list.get(current).push(i);
		}
	}

	public int pop(){
		if(current ==0 && list.get(current).top == -1){
			return -1;
		}
		else {
			if(list.get(current).top != -1){
				return list.get(current).pop();
			}
			else{
				list.remove(current);
				current--;
				return list.get(current).pop();
			}	
		}
	}

	public int popAt(int index){
		return list.get(index).pop();
	}
	
	public static void main(String[] args){
		setOfStacks s = new setOfStacks();
		s.push(0);
		s.push(1);
		s.push(2);
		s.push(3);
		s.push(4);
		s.push(5);
		System.out.println(s.popAt(0));
		System.out.println(s.pop());
		System.out.println(s.pop());
		System.out.println(s.pop());
		System.out.println(s.pop());
		System.out.println(s.pop());
	}
}

