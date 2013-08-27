
public class Main {
	public static void main(String[] args){
		BinaryTree bt = new BinaryTree();
		BinaryTreeNode a = new BinaryTreeNode(1);
		BinaryTreeNode b = new BinaryTreeNode(2);
		BinaryTreeNode c = new BinaryTreeNode(3);
		BinaryTreeNode d = new BinaryTreeNode(4);
		BinaryTreeNode e = new BinaryTreeNode(5);
		BinaryTreeNode f = new BinaryTreeNode(6);
		BinaryTreeNode g = new BinaryTreeNode(7);
		BinaryTreeNode h = new BinaryTreeNode(8);
		BinaryTreeNode i = new BinaryTreeNode(9);
		bt.insertToRoot(a);
		bt.insertToLeft(a, b);
		bt.insertToRight(a, c);
		bt.insertToLeft(b, d);
		bt.insertToRight(b, e);
		bt.insertToLeft(c, f);
		bt.insertToRight(c, g);
		bt.insertToLeft(e, h);
		bt.insertToRight(h, i);
		
		bt.inOrderTraversal(a);
		bt.preOrderTraversal(a);
		bt.postOrderTraversal(a);
		System.out.println("size = " + bt.size(a));
		bt.inOrderTraversalNoRec(a);
		
		
	}
}
