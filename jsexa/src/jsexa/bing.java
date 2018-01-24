package jsexa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class bing {
	int index;
	String title,site,abstracts;
	public bing(int no_of_results){
	
		this.index =no_of_results;
	}
	public static void bing_results(String keyword, int no_of_results) throws Exception 
	{
		try{
			String myDriver = "com.mysql.jdbc.Driver";
		    String myUrl = "jdbc:mysql://localhost:3306/research?autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=utf-8";
		    Class.forName(myDriver);
		    Connection conn = DriverManager.getConnection(myUrl, "root", "password");

		    String initialkeyword = keyword;
		    keyword = keyword.replace(" ", "+");
		    String url = "https://www.bing.com/search?q=" + keyword + "&num=" + String.valueOf(no_of_results);
		    //Connect to the url and obain HTML response
		    Document doc = Jsoup.connect(url).userAgent("chrome").timeout(5000).get();
//		   System.out.println( doc.html());
		    //parsing HTML after examining DOM
		    String title,site,abstracts="";
		    Elements els = doc.select("#b_content .b_algo");
		    for(Element el : els)
		    {
		    	System.out.println("keyword searching: " + keyword + "\n");
		    	Statement st1 = conn.createStatement(); 
		    	System.out.println("Title : " + el.getElementsByTag("a").text());
		    	title = el.getElementsByTag("a").text();
		    	site = el.getElementsByTag("a").attr("href").toString();
		    	abstracts= el.getElementsByTag("p").text().toString();
		    	System.out.println("Site : " + el.getElementsByTag("cite").text());
		    	gethtml gh = new gethtml();
		    	String html =gh.gethtml(site);
		    	abstracts = abstracts.replaceAll("\"", "");
		    	html  = html.replaceAll("\"", "");
		    	System.out.println("Abstract : " + abstracts + "\n");
		    	System.out.println("html : " + html + "\n");
				st1.executeUpdate("INSERT INTO Bing(keyword_Id,Keyword,Tittle,site,abstract,html) " + 
				        "VALUES ((SELECT Keyword_ID from Keyword where Keyword=\""+initialkeyword+"\"),\""+keyword+"\",\""+title+"\",\""+site+"\",\""+abstracts+"\",\""+html+"\")  "); 

		    }
//		    conn.close(); 
			}catch (Exception e) { 
				System.err.println("Got an exception! "); 
				System.err.println("keyword searching:"+keyword); 
				System.err.println(e.getMessage()); 
			} 
	}
	public void run() {
		// TODO Auto-generated method stub
		try {		
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://localhost:3306/research?autoReconnect=true&useSSL=false";
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, "root", "password");
	    
			Statement st = conn.createStatement(); 

			ResultSet rs = st.executeQuery("Select * FROM Keyword ");
	    
			String keyword;
		    while (rs.next())
		      {
		    	keyword =rs.getString("Keyword");
		    	bing_results(keyword,index);
		      }
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
