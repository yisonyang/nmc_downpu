package com.downpu.service;

import com.downpu.Type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class Runner implements ApplicationRunner{
    @Autowired
    Type type;
    @Autowired
    MyProps myProps;
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception{
        for(String s:type.map.keySet()){
            File fileParent=new File(myProps.getBaseDir()+s);
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }
        }
        File fileParent=new File(myProps.getImageDir());
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
    }
}
