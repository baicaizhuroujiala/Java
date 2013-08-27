//use to format date string to mm/dd/yyyy
public class FormatString {
	public static String formateDateString(String original){
		String array[]=original.split("/");		//split string by /
		for (int i=0; i<array.length; i++){
			if (array[i].length()==1){
				array[i]="0"+array[i];
			}
		}
		return array[0]+"/"+array[1]+"/"+array[2];
	}
}
