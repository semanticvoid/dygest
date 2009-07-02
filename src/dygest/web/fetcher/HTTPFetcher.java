/**
 * 
 */
package dygest.web.fetcher;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class deals with all HTTP related functions.
 * @author anand
 *
 */
public class HTTPFetcher {

	/**
	 * Function to fetch inlinks for the given URL.
	 * Uses Yahoo! Site Explorer API.
	 * @param url	the url to fetch the inlinks for
	 * @return	list of inlinks 
	 */
	public static List<String> getInlinks(String url) {
		List<String> inlinks = new ArrayList<String>();
		
		// TODO need to move these constants to a config file
		final String INLINK_APPID = "7YrMUqvV34FTWRfEXCCVpA9z82uMp0Txs7mIkQk4eKnQCEIzuwQNgYn26lz1VJVqFc22hw--";
		final String INLINK_BASEURL = "http://search.yahooapis.com/SiteExplorerService/V1/inlinkData?";
		
		StringBuffer rqstURL = new StringBuffer();
		rqstURL.append(INLINK_BASEURL);
		rqstURL.append("appid=" + INLINK_APPID);
		rqstURL.append("&results=100");
		rqstURL.append("&query=" + url);
		
		HashMap<String, String> data = fetch(rqstURL.toString());
		
		if(data != null && data.containsKey("content")) {
			String xml = data.get("content");
			System.out.println(xml);
		}
		
		return inlinks;
	}
	
	/**
	 * Function to fetch contents of URL
	 * @param url	the url
	 * @return	the contents as String
	 */
	public static HashMap<String, String> fetch(String url) {
		HashMap<String, String> data = null;
		
		try {
			URL u = new URL(url);
			URLConnection uconn = u.openConnection();
			HttpURLConnection conn = (HttpURLConnection) uconn;
			conn.connect();
			String contentType = conn.getContentType();
			StringBuffer content = new StringBuffer();
			InputStream stream = (InputStream) conn.getContent();
			BufferedReader streamRdr = new BufferedReader(new InputStreamReader(stream));
			
			String line;
			while((line = streamRdr.readLine()) != null) {
				content.append(line);
			}
			
			data = new HashMap<String, String>();
			data.put("contentType", contentType);
			data.put("content", content.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
}
