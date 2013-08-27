//Copy the data from the next node into this node and then delete the next node
public class Main{

	private static SingleList deleteNode(SingleList list, SingleNode node){
		node.setData(node.getNext().getData());
		node.setNext(node.getNext().getNext());
		return list;
	}
	public static void main(String[] args){
		SingleList s = new SingleList();
		s.add(1);
		s.add(2);
		s.add(3);
		s.add(4);
		s.add(5);
		s.add(6);
		s.add(7);
		s.add(8);
		SingleNode n = s.find(4);
		deleteNode(s, n).traversal();
	}
}
