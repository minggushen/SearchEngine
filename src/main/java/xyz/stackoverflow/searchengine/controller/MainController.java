package xyz.stackoverflow.searchengine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.stackoverflow.searchengine.highlight.HighlightInformation;
import xyz.stackoverflow.searchengine.highlight.SearchHighlighter;
import xyz.stackoverflow.searchengine.util.Page;

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
			map.put("doclist",displayData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("keywords", keywords);
		return "result";
	}
}
