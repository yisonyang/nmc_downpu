package com.downpu.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "myProps")
public class MyProps {
    private String baseDir;
    private String imageDir;

    public String getImageDir() {
        return imageDir;
    }

    public void setImageDir(String imageDir) {
        this.imageDir = imageDir;
    }

    public String getBaseDir(){
        return baseDir;
    }
    public void setBaseDir(String baseDir){
        this.baseDir=baseDir;
    }
}

