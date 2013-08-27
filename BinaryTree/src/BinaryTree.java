public class BinaryTree{
	public BinaryTreeNode root;
	
	public BinaryTree(){
		root = null;
	}
	
	public void insertToRoot(BinaryTreeNode b){
		if (root == null) {
			root = b;
		}
	}
	
	public void insertToLeft(BinaryTreeNode father, BinaryTreeNode child){
		father.leftChild = child;
	}

	public void insertToRight(BinaryTreeNode father, BinaryTreeNode child){
		father.rightChild = child;
	}

	public void inOrderTraversal(BinaryTreeNode n){
		if (n == null) {
			return;
		}
		else {
			inOrderTraversal(n.leftChild);
			System.out.println(n.data);
			inOrderTraversal(n.rightChild);
		}
	}

	public void postOrderTraversal(BinaryTreeNode n){
		if (n == null) {
			return;
		}
		else {
			postOrderTraversal(n.leftChild);
			postOrderTraversal(n.rightChild);
			System.out.println(n.data);
		}
	}

	public void preOrderTraversal(BinaryTreeNode n){
		if (n == null) {
			return;
		}
		else {
			System.out.println(n.data);
			preOrderTraversal(n.leftChild);
			preOrderTraversal(n.rightChild);	
		}
	}
	
	public int size(BinaryTreeNode n){
		int count = 0;
		if (n == null) {
			return 0;
		}
		else {
			count = 1 + size(n.leftChild) + size(n.rightChild);
			return count;
		}
	}
	
	public void inOrderTraversalNoRec(BinaryTreeNode n){
		int len = this.size(root);
		BinaryTreeNode[] buff = new BinaryTreeNode[len];
		int top = -1;
		BinaryTreeNode i = n;
		while (!(top < 0 && i.leftChild == null && i.rightChild == null)) {
			while (i.leftChild != null){
				buff[++top] = i;
				i = i.leftChild;
			}
			System.out.println(i.data);
			while (i.rightChild == null){
				i = buff[top--];
				System.out.println(i.data);
			}
			i = i.rightChild;
		}
		System.out.println(i.data);
		
	}
	
	public void postOrderTraversalNoRec(BinaryTreeNode n){
		int len = this.size(root);
		BinaryTreeNode[] buff = new BinaryTreeNode[len];
		int top = -1;
		BinaryTreeNode i = n;
		BinaryTreeNode p = null;
		while (i != root){
			if (i != null){
				buff[++top] = i;
				i = i.leftChild;
			}
			else {
				i = buff[top];
				if (i.rightChild != null){
					i = i.rightChild;
					buff[++top] = i;
				}	
			}
		}
		System.out.println(i.data);
	}

	public void preOrderTraversalNoRec(BinaryTreeNode n){
		
	}
	
	
}