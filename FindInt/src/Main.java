import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;


public class Main {
	
	public static void findInt(int[] array){
		//create a hash table and record the count
		Hashtable<Integer, Integer> ht = new Hashtable<Integer, Integer>();
		for(int i=0; i<array.length; i++){
			if(ht.containsKey(array[i])){
				ht.put(array[i], ht.get(array[i])+1);
			}
			else{
				ht.put(array[i], 1);
			}
		}
		sortValue(ht);
		
	}

	public static void sortValue(Hashtable<Integer, Integer> t) {
		// Transfer as List and sort it
		ArrayList<Map.Entry<Integer, Integer>> l = new ArrayList<Entry<Integer, Integer>>(t.entrySet());
		Collections.sort(l, new Comparator<Map.Entry<Integer, Integer>>() {
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		System.out.println(l);
	}
	
	public static void main(String[] args) {
		int[] a = {1, 2, 3 ,4 ,4, 6, 4 ,2, 4, 1};
		findInt(a);
	}

}
