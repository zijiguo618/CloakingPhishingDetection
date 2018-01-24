package jsexa;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class webdriver {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String torPath = "/Applications/TorBrowser.app/Contents/MacOS/firefox";
		String profilePath = "/Users/zijiguo/Library/Application Support/Firefox/Profiles/ziji_tor_profile";
//		File f = new File("/Users/zijiguo/Library/Application Support/Firefox/Profiles/ziji_tor_profile");
    
        

		
		FirefoxProfile profile = new FirefoxProfile(new File(profilePath));
		FirefoxBinary binary = new FirefoxBinary(new File(torPath));
		FirefoxDriver driver = new FirefoxDriver(binary, profile);
		driver.get("http://www.baidu.com");
	}

}
