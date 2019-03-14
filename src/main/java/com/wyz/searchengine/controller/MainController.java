package com.wyz.searchengine.controller;

import com.wyz.searchengine.highlight.HighlightInformation;
import com.wyz.searchengine.highlight.SearchHighlighter;
import com.wyz.searchengine.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/query")
	public String query(@RequestParam("keywords") String keywords, Map<String,Object> map) {
		ArrayList<HighlightInformation> resultsFetched = new ArrayList<HighlightInformation>();
		List<Page> list = null;
		try {
			ArrayList<HighlightInformation> displayData = SearchHighlighter.getDoc(keywords, resultsFetched);
			map.put("docList",displayData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("keywords", keywords);
		return "result";
	}
}
