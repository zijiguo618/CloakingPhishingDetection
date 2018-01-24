package jsexa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

public class main {

	public static void main(String[] args) throws Exception {
		
		// TODO Auto-generated method stub		
		keyword keys =new keyword();
		try{

//insert keyword into DB,Start searching
				bing bing = new bing(10);
				bing.run();
//		    	 googleResults google = new googleResults(10);
//		    	 google.run();
		    	 test baidu = new test(10);
		    	 baidu.run();
			
		  
	   
		}catch (Exception e) { 
	        System.err.println("Got an exception! "); 
	        System.err.println(e.getMessage()); 
	    }
		
		
		
//		
		
//		
		
	}

}
