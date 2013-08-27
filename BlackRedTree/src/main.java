
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = {12, 1, 9, 2, 0, 11, 7, 19, 4, 15, 18, 5, 14, 13, 10, 16, 6, 3, 8, 17};
		RedBlackTree bt = new RedBlackTree();
		for(int i = 0; i<a.length; i++){
			System.out.println("After insert "+ a[i]);
			bt.insert(a[i]);
		}
	}

}
