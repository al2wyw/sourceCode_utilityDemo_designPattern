package test1;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class urlWrapper {
	private URL url;
	public urlWrapper(URL url){
		this.url = url;
	}
	public urlWrapper(){
		
	}
	public urlWrapper(String spec) throws MalformedURLException{
		url = new URL(spec);
	}
	public URLConnection openConnection() throws IOException{
		return url.openConnection();
	}
	
}
