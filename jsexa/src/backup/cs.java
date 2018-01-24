package backup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

//import jsexa.googleResults.Result;

public class cs {

	public static void main(String[] args) throws Exception {

	    String key="AIzaSyBqZgyQojRBiZmovNryS8EYgJLrH-LuNtU";
	    String qry="iastate";

	    URL url = new URL(
	            "https://www.googleapis.com/customsearch/v1?key="+key+ "&cx=003639921848042463703:oxqarv4lcwe&q="+ qry + "&alt=json");
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Accept", "application/json");
	    BufferedReader br = new BufferedReader(new InputStreamReader(
	            (conn.getInputStream())));

	    String output;
	    
	    System.out.println("Output from Server .... \n");
	    while ((output = br.readLine()) != null) {
	    	//System.out.println(output);
	        if(output.contains("\"link\": \"")){                
	            String link=output.substring(output.indexOf("\"link\": \"")+("\"link\": \"").length(), output.indexOf("\","));
	            System.out.println(link);       //Will print the google search links
	        }     
	    }
	    conn.disconnect();    
	    
	}

}
