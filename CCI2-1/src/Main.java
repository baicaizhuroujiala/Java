import java.util.Hashtable;


public class Main {
	
	public static SingleList removeDuplicates(SingleList s) {
		Hashtable<Integer, Boolean> table = new Hashtable<Integer, Boolean>();
		SingleNode n = s.getHead();
		SingleNode b = null;
		table.put(n.getData(), true);
		b=n;
		while (n.getNext()!=null){
			if (table.containsKey(n.getData())){
				;
			}
			else {
				table.put(n.getData(), true);
				b.setNext(n);
				b = n;
			}
			n = n.getNext();
		}
		if (table.containsKey(s.getTail().getData())){
			s.setTail(b);
			b.setNext(null);
		}
		else{
			b.setNext(s.getTail());
		}
		return s;
	}
	
	public static SingleList removeDuplicates2(SingleList s) {
		SingleNode before = s.getHead();
		SingleNode current = s.getHead().getNext();
		SingleNode runner = s.getHead();
		while(current.getNext() != null){  	//last 2
			//should use current != null, then we do not need to deal with 
			runner = s.getHead();
			while(runner != current){				
				if (current.getData() == runner.getData()){
					before.setNext(current.getNext());
					current = before;
					break;
				}
				runner = runner.getNext();
			}
			before = current;
			current = current.getNext();
		}
		current = s.getTail();
		runner = s.getHead();
		while(runner != current){
			if (current.getData() == runner.getData()){
				before.setNext(null);
				current = before;
				s.setTail(current);
				break;
			}
			runner = runner.getNext();
		}
		return s;
	}

	public static void main(String[] args) {
		SingleList s = new SingleList();
		s.add(1);
		s.add(2);
		s.add(3);
		s.add(3);
		s.add(5);
		s.add(1);
		s.add(2);
		s.add(6);
		removeDuplicates2(s).traversal();
	}
}
