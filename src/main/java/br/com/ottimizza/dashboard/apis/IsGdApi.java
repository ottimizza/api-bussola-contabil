package br.com.ottimizza.dashboard.apis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IsGdApi {
	
	public String shortURL(String urlToShort) {
	    URL url;
	    HttpURLConnection conn;
	    BufferedReader br = null;
	    String line;
	    try {
	        url = new URL("https://is.gd/create.php?format=simple&url=" + urlToShort);
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestProperty("User-Agent", "NING/1.0");
	        conn.setRequestProperty("Cookie", "foo=bar");
	        conn.setRequestMethod("GET");
	        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        line = "" + br.readLine();
	        if (!line.equals("")) {
	            return line;
	        }
	    } catch (Exception e) { }
	    return null;
	}
}