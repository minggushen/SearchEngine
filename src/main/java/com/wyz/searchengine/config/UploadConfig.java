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
            path = "D:\\lucene\\mine\\";
        }
        UploadConfig.path = path;
    }

    public static String getOutPath(){
        return path + "out\\";
    }
    public static String getInPath(){
        return path + "in\\";
    }
}
