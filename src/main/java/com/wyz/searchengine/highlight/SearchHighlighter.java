package com.wyz.searchengine.highlight;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SearchHighlighter {
	public static ArrayList<HighlightInformation> getDoc(String args, ArrayList<HighlightInformation> resultsFetched)
			throws Exception {
		String INDEX_DIR = "D:\\lucene\\mine\\out";
		Directory dir = FSDirectory.open(Paths.get(INDEX_DIR));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();
		QueryParser qp = new QueryParser("contents", analyzer);
		Query query = null;
		if (args.toLowerCase().contains("not")) {
			String[] split = args.toLowerCase().split("not");
			Query q1 = qp.parse(split[0]);
			Query q2 = qp.parse(split[1]);
			query = new BooleanQuery.Builder().add(q1, BooleanClause.Occur.MUST).add(q2, BooleanClause.Occur.MUST_NOT)
					.build();
		} else {
			if (args.toLowerCase().contains("and")) {
				String[] split = args.toLowerCase().split("and");
				Query query1 = qp.parse(split[0]);
				Query query2 = qp.parse(split[1]);
				query = new BooleanQuery.Builder().add(query1, BooleanClause.Occur.MUST)
						.add(query2, BooleanClause.Occur.MUST).build();
			} else {
				if (args.toLowerCase().contains("or")) {
					String[] split = args.toLowerCase().split("or");
					Query query1 = qp.parse(split[0]);
					Query query2 = qp.parse(split[1]);
					query = new BooleanQuery.Builder().add(query1, BooleanClause.Occur.SHOULD)
							.add(query2, BooleanClause.Occur.SHOULD).build();
				} else {
					query = qp.parse(args);
				}
			}
		}

		TopDocs hits = searcher.search(query, 10);
		Formatter formatter = new SimpleHTMLFormatter();
		QueryScorer scorer = new QueryScorer(query);
		Highlighter highlighter = new Highlighter(formatter, scorer);
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer, 20);
		highlighter.setTextFragmenter(fragmenter);

		for (int i = 0; i < hits.scoreDocs.length; i++) {
			int docid = hits.scoreDocs[i].doc;
			Document doc = searcher.doc(docid);
			String text = doc.get("contents");
			TokenStream stream = TokenSources.getAnyTokenStream(reader, docid, "contents", analyzer);
			HighlightInformation giveResult = new HighlightInformation();
			giveResult.setDocHits(hits.totalHits);
			giveResult.setDocIndexScore(hits.scoreDocs[i].score);
			giveResult.setPath(doc.get("path"));
			String[] frags = highlighter.getBestFragments(stream, text, 50);
			List<String> longFrags = new ArrayList<>();
			for (String s: frags) {
				if(s.length() > 30){
					longFrags.add(s);
				}
			}
			String[] strings = new String[longFrags.size()];
			longFrags.toArray(strings);
			giveResult.setSearchHighlightTextResult(strings);
			resultsFetched.add(giveResult);
		}
		dir.close();
		return resultsFetched;
	}
}