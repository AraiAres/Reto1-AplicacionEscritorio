package com.gymsync.app.connectivity.connection;

import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionManager {
	public boolean isOnline() {
		try {
			URL url = new URL("https://www.google.com");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("HEAD");
			connection.setConnectTimeout(2000);
			connection.connect();
			return connection.getResponseCode() == 200;
		} catch (Exception e) {
			return false;
		}
	}
}