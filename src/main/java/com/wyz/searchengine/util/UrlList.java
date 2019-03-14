package com.wyz.searchengine.util;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UrlList {
	
	private Queue<String> unVisitedUrl;
	
	public UrlList() {
		unVisitedUrl = new LinkedList<String>();
	}
	
	public Queue<String> getUnVisitedUrl() {
		return unVisitedUrl;
	}
	
	public void setUnVisitedUrl(Queue<String> unVisitedUrl) {
		this.unVisitedUrl = unVisitedUrl;
	}
	
	public synchronized boolean isEmpty() {
		return unVisitedUrl.isEmpty();
	}

	public synchronized boolean isExist(String url) {
		if(unVisitedUrl.contains(url))
			return true;
		else
			return false;
	}
	
	public synchronized boolean addUrl(String url) {
		if(isExist(url))
			return false;
		else {
			unVisitedUrl.add(url);
			return true;
		}
	}
	
	public synchronized String pollUrl() {
		if(isEmpty()) {
			return null;
		}else {
			String url = unVisitedUrl.poll();
			return url;
		}
	}
}
