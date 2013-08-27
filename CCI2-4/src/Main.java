public class Main{
	public static SingleList add(SingleList a, SingleList b){
		SingleNode an = a.getHead();
		SingleNode bn = b.getHead();
		SingleList result = new SingleList();
		int add = 0;
		int temp;
		while (an != null && bn != null) {
			temp = an.getData() + bn.getData() + add;
			add = 0;
			if (temp >= 10) {
				add = 1;
				temp = temp - 10;
			}
			result.add(temp);
			an = an.getNext();
			bn = bn.getNext();
		}
		if (an == null) {
			while (bn != null ){
				temp = bn.getData() + add;
				add = 0;
				if (temp >= 10) {
					add = 1;
					temp = temp - 10;	
				}
				result.add(temp);
				bn = bn.getNext();
			}
		}
		if (bn == null) {
			while (an != null ){
				temp = an.getData() + add;
				add = 0;
				if (temp >= 10) {
					add = 1;
					temp = temp - 10;	
				}
				result.add(temp);
				an = an.getNext();
			}
		}
		if (add != 0) {
			result.add(add);
		}
		return result;
	}
	public static void main(String[] args){
		SingleList a = new SingleList();
		SingleList b = new SingleList();
		a.add(1);
		a.add(9);
		a.add(2);
		b.add(9);
		b.add(0);
		b.add(8);
		b.add(1);
		b.add(1);
		add(a,b).traversal();
	}
}