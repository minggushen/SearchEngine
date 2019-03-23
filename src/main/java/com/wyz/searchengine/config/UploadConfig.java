package com.wyz.searchengine.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wangyaozhou
 */
@Component
public class UploadConfig {

    public static String path;

    @Value("${upload.path:null}")
    public void setPath(String path) {
        if(path == null || "null".equals(path)){
            //如果是windows
            if(System.getProperty("os.name").toLowerCase().startsWith("win")){
                path = "D:\\lucene\\mine";
            }else {
                path = "\\opt\\lucene\\mine";
            }
        }
        UploadConfig.path = path;
    }

    public static String getOutPath(){
        return path + "\\out\\";
    }
    public static String getInPath(){
        return path + "\\in\\";
    }
}
