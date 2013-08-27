
public class SingleList {
	private SingleNode head;
	private SingleNode tail;
	
	public SingleNode getHead() {
		return head;
	}
	public void setHead(SingleNode head) {
		this.head = head;
	}
	public SingleNode getTail() {
		return tail;
	}
	public void setTail(SingleNode tail) {
		this.tail = tail;
	}
	public SingleList() {					//initialize
		head = null;
		tail = null;
	}
    public int length() {					//length of the list
		int count = 1;
		SingleNode n = head;
		while (n.getNext()!=null){
			count ++;
			n = n.getNext();
		}
		return count;
    }
    public void add(int x) {				//add element to the end of the list
    	SingleNode n = new SingleNode(x);
    	if(this.isEmpty()) {
    		head = n;
    		tail = n;
    	}
    	else{
    		tail.setNext(n);
    		tail = n;
    	}
    }
    public void traversal(){				//traversal the list and print out.
    	SingleNode n=head;
    	while (n.getNext() != null) {
    		System.out.println(n.getData());
    		n = n.getNext();
    	}
    	System.out.println(tail.getData());
    }
    public boolean isEmpty(){				//judge the list is empty or not.
    	if (head == null && tail == null){
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    public SingleNode find(int x){					//find first element which data is x and return the address of the node
		SingleNode n = head;
		while (n.getNext() != null){
			if (n.getData() == x){
				return n;
			}
			n = n.getNext();
		}
		if (tail.getData() == x){
			return tail;
		}
		else
			return null;						 
    }
    void delete(int x){						//delete first element which data is 
    	SingleNode n = head;
    	SingleNode b = null;
		while (n.getNext() != null){
			if (n.getData() == x){
				break;
			}
			b = n;
			n = n.getNext();
		}
    	if (n == head){
    		this.setHead(n.getNext());
    	}
    	else if (n == tail){
    		this.setTail(b);
    		b.setNext(null);
    	}
    	else {
    		b.setNext(n.getNext());
    	}
    }
    void insert(int x,SingleNode p){		//add a node after p
    	SingleNode s = new SingleNode(x);
    	if (p == tail){
    		tail = s;
    		p.setNext(s);
    	}
    	else{
    		SingleNode temp = p.getNext();
    		p.setNext(s);
    		s.setNext(temp);
    	}
    }
    void insertHead(int x){					//add a node before head
    	SingleNode s = new SingleNode(x);
    	s.setNext(head);
    	head = s;
    }

}
