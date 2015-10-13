package com.jrsys.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

public class WTF {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		URL url= new URL("http://udn.com");
		InputStream in=url.openStream();
		String theString = IOUtils.toString(in);
		System.out.println(theString);

	}

}
