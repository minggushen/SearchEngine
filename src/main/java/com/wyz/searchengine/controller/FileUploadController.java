package com.wyz.searchengine.controller;

import com.wyz.searchengine.config.UploadConfig;
import com.wyz.searchengine.util.FileIndexer;
import com.wyz.searchengine.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 文件上传
 * @author wangyaozhou
 */
@Controller
public class FileUploadController {

    private static Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        //1、将文件放到指定目录
        String path = UploadConfig.getInPath() + file.getOriginalFilename();
        File file1 = new File(UploadConfig.getInPath());
        if(!file1.exists()){
            boolean mkdirs = file1.mkdirs();
            logger.info("返回：{}",mkdirs);
        }

        FileUtils.write(path, file.getInputStream());
        //2、将文件进行索引
        FileIndexer.indexFile(path,UploadConfig.getOutPath());

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println("<script>");
        writer.println("alert('上传并索引成功！');");
        writer.println("</script>");
        writer.flush();
        return "index";
    }

//    @RequestMapping("/query")
//    public String query(@RequestParam("keywords") String keywords, Map<String,Object> map) {
//        ArrayList<HighlightInformation> resultsFetched = new ArrayList<HighlightInformation>();
//        List<Page> list = null;
//        try {
//            ArrayList<HighlightInformation> displayData = SearchHighlighter.getDoc(keywords, resultsFetched);
//            map.put("docList",displayData);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        map.put("keywords", keywords);
//        return "result";
//    }
}
