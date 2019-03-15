package com.wyz.searchengine.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileIndexer {
    private static Logger logger = LoggerFactory.getLogger(FileIndexer.class);

	public static final String FIELD_NAME = "filename";

	public static void indexFile(String docsPath,String indexPath){

		// Input Path Variable
		final Path docDir = Paths.get(docsPath);

		try {
			// org.apache.lucene.store.Directory instance
			Directory dir = FSDirectory.open(Paths.get(indexPath));

			// analyzer with the default stop words
			Analyzer analyzer = new StandardAnalyzer();

			// IndexWriter Configuration
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);

			// IndexWriter writes new index files to the directory
			IndexWriter writer = new IndexWriter(dir, iwc);

			// Its recursive method to iterate all files and directories
			indexDocs(writer, docDir);

			writer.close();
		} catch (IOException e) {
            logger.error("io异常！",e);
		}
	}


	static void indexDocs(final IndexWriter writer, Path path) throws IOException {
		// Directory?
		if (Files.isDirectory(path)) {
			// Iterate directory
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					try {
						FileHandlingParser fileParser = new FileHandlingParser();

						fileParser.checkFile(file);
						// Index this file

						indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
					} catch (IOException ioe) {
                        logger.error("io异常！",ioe);
					} catch (SAXException e) {
                        logger.error("SAXException异常！",e);
					} catch (Exception e) {
                        logger.error("异常！",e);
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} else {
			// Index this file
			indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis());
		}
	}

	static void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException {
		try (InputStream stream = Files.newInputStream(file)) {

			String fileType = file.toString().substring(file.toString().length() - 4, file.toString().length());
            logger.info("indexDoc:- " + file + " fileType is:- " + fileType);

			Document doc = new Document();
            Path fileName = file.getFileName();
            String fileNameString = fileName.toString();
            doc.add(new StringField("path", file.toString(), Store.YES));
            doc.add(new LongPoint("modified", lastModified));
            doc.add(new TextField(FIELD_NAME, file.toString(), Store.YES));
            if (fileNameString.endsWith("pdf")) {
				PDFParser parser = new PDFParser(stream);
				parser.parse();
				COSDocument cd = parser.getDocument();
				PDFTextStripper stripper = new PDFTextStripper();
				String text = stripper.getText(new PDDocument(cd));
				cd.close();
				doc.add(new TextField("contents", text, Store.YES));
			} else if (fileNameString.endsWith("doc")){
                FileInputStream fis = new FileInputStream(file.toFile());
                HWPFDocument document = new HWPFDocument(fis);
                StringBuilder text = document.getText();
				doc.add(new TextField("contents", text.toString(), Store.YES));
                fis.close();
			}else if (fileNameString.endsWith("docx")){
                FileInputStream fis = new FileInputStream(file.toFile());
                XWPFDocument xdoc = new XWPFDocument(fis);
                XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
                doc.add(new TextField("contents", extractor.getText(), Store.YES));
                fis.close();
            }
            writer.updateDocument(new Term("path", file.toString()), doc);
		}catch (Exception e){
            logger.error("创建索引异常！",e);
        }
	}
}
