package eu.michalbuda.offerGenerator.scraper;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Scraper {
	private static WebClient webClient;
	private URL url;
	private boolean setJavaScriptEnabled = true;
	private HtmlPage page;
	


	private void webClientStart(){
		System.out.println("Starting webClient");
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
		//com.gargoylesoftware.htmlunit.BrowserVersion.setDefault(com.gargoylesoftware.htmlunit.BrowserVersion.CHROME);
		
		webClient = new WebClient(BrowserVersion.FIREFOX_52);
		webClient.getOptions().setJavaScriptEnabled(setJavaScriptEnabled);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
	}
	
	private void webClientStop() {
		webClient.close();
	}
	
	private void openUrl(URL url) throws FailingHttpStatusCodeException, IOException {
		System.out.println("Opening url: " + url);
		page = webClient.getPage(url);
        //webClient.waitForBackgroundJavaScript(30 * 500);
	}
}
