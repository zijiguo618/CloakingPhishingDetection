package jsexa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class googleResults implements Runnable {

	int index;
	String title,site,abstracts;
	public googleResults(int no_of_results){
	
		this.index =no_of_results;
	}

public static void google_results(String keyword, int no_of_results) throws Exception 
{
//Replace space by + in the keyword as in the google search url
	try{
		String myDriver = "com.mysql.jdbc.Driver";
	    String myUrl = "jdbc:mysql://localhost:3306/research?autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=utf-8";
	    Class.forName(myDriver);
	    Connection conn = DriverManager.getConnection(myUrl, "root", "password");

//    st.executeUpdate("INSERT INTO Keyword(keyword) " + 
//        "VALUES (\""+keyword+"\")"); 
	   String initialkeyword = keyword;
	keyword = keyword.replace(" ", "+");
	String url = "https://www.google.com/search?q=" + keyword + "&num=" + String.valueOf(no_of_results);
//Connect to the url and obain HTML response
	Document doc = Jsoup.connect(url).userAgent("chrome").timeout(5000).get();
//parsing HTML after examining DOM
	String title,site,abstracts="";
	Elements els = doc.select("#res .g");
	for(Element el : els)
	{
		System.out.println("keyword searching: " + keyword + "\n");
		Statement st1 = conn.createStatement(); 
		System.out.println("Title : " + el.getElementsByTag("h3").text());
		 title = el.getElementsByTag("h3").text();
		 site = el.getElementsByTag("cite").text().toString();
		 abstracts= el.getElementsByTag("span").text().toString();
		System.out.println("Site : " + el.getElementsByTag("cite").text());
		gethtml gh = new gethtml();
		String html =gh.gethtml(site);
		abstracts = abstracts.replaceAll("\"", "");
		html  = html.replaceAll("\"", "");
		System.out.println("Abstract : " + abstracts + "\n");
		//System.out.println(html);
		st1.executeUpdate("INSERT INTO Google(keyword_Id,Keyword,Tittle,site,abstract,html) " + 
		        "VALUES ((SELECT Keyword_ID from Keyword where Keyword=\""+initialkeyword+"\"),\""+keyword+"\",\""+title+"\",\""+site+"\",\""+abstracts+"\",\""+html+"\")  "); 
		
	}
	conn.close(); 
	}catch (Exception e) { 
        System.err.println("Got an exception! "); 
        System.err.println("keyword searching:"+keyword); 
        System.err.println(e.getMessage()); 
    } 
	
	}

@Override
public void run() {
	// TODO Auto-generated method stub
	try {		
		String myDriver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://localhost:3306/research?autoReconnect=true&useSSL=false";
		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, "root", "password");
    
		Statement st = conn.createStatement(); 
//    
//st.executeUpdate("INSERT INTO Keyword(keyword) " + 
//    "VALUES (\""+keyword+"\")"); 
		ResultSet rs = st.executeQuery("Select * FROM Keyword ");
    
		String keyword;
	    while (rs.next())
	      {
	    	keyword =rs.getString("Keyword");
	    	google_results(keyword,index);
	      }
	
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}