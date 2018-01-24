package jsexa;

import java.io.IOException;
import java.sql.Statement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class testGooglefetching {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String url = "https://www.google.com/search?q=Angel+investment&num=10";
		//Connect to the url and obain HTML response
			Document doc = Jsoup.connect(url).userAgent("Mozilla/17.0").timeout(30000).get();
			Elements els = doc.select("#res .g");
			String title,site,abstracts="";
			for(Element el : els)
			{
//				System.out.println("keyword searching: " + keyword + "\n");
				//Statement st1 = conn.createStatement(); 
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
//				st1.executeUpdate("INSERT INTO Google(keyword_Id,Keyword,Tittle,site,abstract,html) " + 
//				        "VALUES ((SELECT Keyword_ID from Keyword where Keyword=\""+initialkeyword+"\"),\""+keyword+"\",\""+title+"\",\""+site+"\",\""+abstracts+"\",\""+html+"\")  "); 
				
			}
//			conn.close(); 
	}

}
