package com.wyz.searchengine.util;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtil {
	
	public static boolean isValid(String url) {
		if(url.equals("/") || url.startsWith("#") || url.startsWith("javascript:") || url.startsWith("mailto:") || url.startsWith("void(0)") || url.equals("") || url.startsWith(";") || url.startsWith(":")) {
			return false;
		}else {
			return true;
		}
	}
	
	private static String escapeHtml(String url) {
		url = url.replaceAll("&amp;", "&");
		url = url.replaceAll("lt;", "<");
		url = url.replaceAll("&gt;", ">");
		url = url.replaceAll("&quot;", "\"");
		return url;
	}
	
	public static String joinUrl(String rootUrl,String url) {
		if(!isValid(url))
			return "";
		else {
			if(url.endsWith("/"))
				url = url.substring(0, url.length()-1);
			
			if(!url.startsWith("http")) {
				if(rootUrl.endsWith("/"))
					rootUrl = rootUrl.substring(0, rootUrl.length()-1);
				if(url.startsWith("/"))
					url = url.substring(1);
				url = rootUrl+"/"+url;
			}
			if(url.contains("#")) {		
				url = url.substring(0, url.indexOf("#"));
			}
			url = escapeHtml(url);
			return url;
		}
	}
	
	public static boolean domainIsValid(String url,String domain){
		URL u = null;
		try {
			u = new URL(url);
		} catch (MalformedURLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return domain.equals(u.getHost());
	}
}
