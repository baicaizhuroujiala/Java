import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Main {
	

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		//make up a twitter search API
		String urlstr = "http://search.twitter.com/search.json?q=";

		System.out.print("Search for : ");
		//input a hashtag
		String temp = in.readLine();
		//replace "#" with "%23"
		if (temp.charAt(0) == '#'){
			urlstr = urlstr + "%23" + temp.subSequence(1, temp.length());
		}
		//since 2000-05-01
		//return per page is max, 100
		//include entities is true
		//result tupe is recent
		urlstr += "&since=2000-05-01&rpp=100&include_entities=1&result_type=recent";

		URL url = new URL(urlstr);
		//get result
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
		
		
		//get "url" field in json
		int c;
		String s = "";
		while ((c = br.read()) != -1) {
			if((char) c == '"'){
				if((c = br.read()) != -1 && (char) c == 'u'){
					if((c = br.read()) != -1 && (char) c == 'r'){
						if((c = br.read()) != -1 && (char) c == 'l'){
							if((c = br.read()) != -1 && (char) c == '"'){
								c = br.read();
								c = br.read();
								while ((c = br.read()) != -1 && (char) c != '"'){
									if ((char) c != '/'){
										s += (char) c;
									}
								}
								System.out.println(s);
								s="";
							}
							else{
								continue;
							}
						}
						else{
							continue;
						}
					}
					else{
						continue;
					}
				}
				else{
					continue;
				}
			}
		}
		br.close();

	}

}
