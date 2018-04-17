package com.downpu.Type;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Component
public class Type {
    public static LinkedHashMap<String,String> map=new LinkedHashMap();
     public Type(){
        map.put("操作系统","1");
        map.put("编程开发","2");
        map.put("行业软件","3");
        map.put("安全软件","4");
        map.put("办公软件","5");
        map.put("系统工具","6");
        map.put("驱动","7");
        map.put("媒体播放","8");
    }
}
