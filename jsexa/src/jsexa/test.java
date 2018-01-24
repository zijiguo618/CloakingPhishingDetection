package jsexa;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class test implements Runnable{

	int index;
	public test( int index){

		this.index = index;
	}
	
	public static void baidu_result(String keyword, int no_of_results) throws Exception{
		try{
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://localhost:3306/research?autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=utf-8";

		    Class.forName(myDriver);
		    Connection conn = DriverManager.getConnection(myUrl, "root", "password");
		    
		    Statement st = conn.createStatement(); 
//	   
//	    st.executeUpdate("INSERT INTO Keyword(keyword) " + 
//	        "VALUES (\""+keyword+"\")"); 
		    System.out.println("Baidu Keyword searching: " + keyword + "\n");
		    String initial_keyword = keyword;
		keyword = keyword.replace(" ", "+");
		
		String url ="http://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=baidu&wd="+keyword;
		Document doc = Jsoup.connect(url).userAgent("chrome").timeout(50000).get();
		int index =0;
		String tempurl,tempabstract,temptittle,site,abstracts="";
		int index_tittle;
	//System.out.println("start fetching");
		for (;index<no_of_results;index++) {
			//System.out.println(index+"\t"+no_of_results);
			 tempurl=doc.select("#content_left .t").get(index).getElementsByTag("a").attr("href");
			 tempabstract = doc.select("#content_left div#"+(index+1)+".c-container").text().toString();
			 temptittle =doc.select("#content_left .t").get(index).text().toString();
			 index_tittle=temptittle.length();
			 site = findrealurl(tempurl);
			 gethtml gh = new gethtml();
		    	String html =gh.gethtml(site);
		    	html  = html.replaceAll("\"", "");
			// System.out.println(index+"\t"+no_of_results+"\t"+index_tittle);
			 if(index_tittle>=0){
				 abstracts =findrealabstract(tempabstract,index_tittle).toString();
				 //System.out.println("if condition done");	 
			 }
				 else{
					 abstracts =null;
				 }
			 Charset.forName("UTF-8").encode(temptittle);
//			System.out.println("tittle: " + temptittle);
//			System.out.println("site: " + site);
			Charset.forName("UTF-8").encode(abstracts);
//			System.out.println("abstract: " + abstracts); 
			abstracts = abstracts.replaceAll("\"", "");
//			System.out.println("abstract: " + abstracts); 
//			ResultSet rs = st.executeQuery("SELECT * FROM research.Keyword WHERE Keyword = \""+initial_keyword+"\"");
//			if(rs.next()){
//			System.out.println("Keyword_ID:"+rs.getInt("Keyword_ID"));}
			st.executeUpdate("INSERT INTO Baidu(keyword_Id,Keyword,Tittle,site,abstract,html) " + 
			        "VALUES ((SELECT Keyword_ID from Keyword where Keyword=\""+initial_keyword+"\"),\""+keyword+"\",\""+temptittle+"\",\""+site+"\",\""+abstracts+"\",\""+html+"\")  "); 
		
		}
		}catch (Exception e) { 
	        System.err.println("Got an exception! "); 
	        System.err.println(e.getMessage()); 
	    } 
	}
	
	public static String findrealurl(String url) throws IOException{
			Document doc = Jsoup.connect(url).userAgent("chrome").timeout(5000).get();
			String Tittle = doc.select("meta").get(0).attr("content");
			return Tittle.substring(7, Tittle.length()-1);
	}
	public static String findrealabstract(String str, int index){
		String temp = str;
		int end = str.lastIndexOf("...");
		//System.out.println("temp:"+temp+" end:"+end +" String length:"+temp.length());
		if(end>=0){
			//System.out.println("returning:");
			String result = temp.substring(index, end);
			//System.out.println("returning:"+  result);
			return result;
			}
			else{
				return temp;
			}
	}
	public String changeCharset(String str)  
            throws UnsupportedEncodingException {  
        if (str != null) {  
            // 用源字符编码解码字符串  
            byte[] bs = str.getBytes();  
            return new String(bs, "UTF_8");  
        }  
        return null;  
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
			ResultSet rs = st.executeQuery("Select * FROM Keyword ");
		    
			String keyword;
		    while (rs.next())
		      {
		    	keyword =rs.getString("Keyword");
		    	baidu_result(keyword,index);
		      }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
