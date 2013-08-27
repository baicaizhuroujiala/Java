import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;


public class Hash1 {
	public static void main(String[] args){
		HashMap<String, String> h = new HashMap<String, String>();
		h.put("c", "china");
		h.put("a", "america");
		h.put("b", "british");
		System.out.println(h.get("b"));
		
		//use iterator to iterate HashTable
		Iterator<String> i = h.keySet().iterator();
		while(i.hasNext()){
			String key = i.next().toString();
			System.out.println("key = " + key);
			System.out.println("value = " + h.get(key));
		}
		
		//use for loop
		Set<Entry<String, String>> s = h.entrySet();
		for(Entry<String, String> entry :s) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
		
		
	}
}
