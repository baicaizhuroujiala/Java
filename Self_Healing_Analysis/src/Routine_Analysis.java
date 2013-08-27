import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Routine_Analysis {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ArrayList<Routine_Info> list1 = new ArrayList<Routine_Info>();
		readFileByLines("C:/pin-2.12-58423-msvc10-windows/r1.out", list1);
		ArrayList<Routine_Info> list2 = new ArrayList<Routine_Info>();
		readFileByLines("C:/pin-2.12-58423-msvc10-windows/r10.out", list2);
		
		/*
		Scanner in = new Scanner(System.in);
		String address = in.nextLine();
		ArrayList<Routine_Info> t = findFunctionByAddress(address, list1);
		for(int i=0; i<t.size(); i++){
			t.get(i).print();
		}
		*/
		
		compareResults(list1, list2);
		/*
		for(int i=0; i<list3.size(); i++){
			list3.get(i).print();
		}
		*/
		
	}

    public static void readFileByLines(String fileName, ArrayList<Routine_Info> list) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            Routine_Info lastInfo = null;
            while ((tempString = reader.readLine()) != null) {
            	String tempArray[] = tempString.split("\t");
                if(tempArray[0].equals("Name ")){
                	Routine_Info ri = new Routine_Info();
                	ri.name = tempArray[1];
                	list.add(ri);
                	lastInfo = list.get(list.size()-1);
                }
                else if(tempArray[0].equals("Image ")){
                	lastInfo.image = tempArray[1];
                }
                else if(tempArray[0].equals("Start ")){
                	lastInfo.start = Long.parseLong(tempArray[1], 16);
                }
                else if(tempArray[0].equals("Tail ")){
                	lastInfo.tail = Long.parseLong(tempArray[1], 16);
                }
                else if(tempArray[0].equals("Count ")){
                	lastInfo.count = Long.parseLong(tempArray[1], 16);
                }
                else if(tempArray[0].equals("Return value ")){
                	lastInfo.returnValue.add(Long.parseLong(tempArray[1], 16));
                }
                else if(tempArray[0].equals("Return address ")){
                	lastInfo.returnAddress.add(Long.parseLong(tempArray[1], 16));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
    
    public static ArrayList<Routine_Info> findFunctionByAddress(String address, ArrayList<Routine_Info> list){
    	long l = Long.parseLong(address, 16);
    	ArrayList<Routine_Info> temp = new ArrayList<Routine_Info>();
		for(int i=0; i<list.size(); i++){
			Routine_Info ri = list.get(i);
			if(ri.start <= l && ri.tail >= l){
				temp.add(ri);
			}
		}
    	return temp;
    }

    public static void compareResults(ArrayList<Routine_Info> list1, ArrayList<Routine_Info> list2){
    	ArrayList<Routine_Info> temp = new ArrayList<Routine_Info>();    	
    	for(int i=0; i<list1.size(); i++){
			Routine_Info ri1 = list1.get(i);
			Routine_Info ri2 = list2.get(i);
    		if(ri1.name.equals(ri2.name)){
    			for(int j=0; j<ri1.returnValue.size(); j++){
    				long r1 = ri1.returnValue.get(j);
    				long r2 = ri2.returnValue.get(j);
    				if(r1 != r2){
    					System.out.println(ri1.name + " " + j + "\nNew " + r1 + "\nOld " + r2 + "\n");
    				}
    			}
    		}
    	}
    	
    }
}
