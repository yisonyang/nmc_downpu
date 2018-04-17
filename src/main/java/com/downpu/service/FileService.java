package com.downpu.service;

import com.downpu.Type.Type;
import com.downpu.repository.DownItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService {
    @Autowired
    DownItemRepository downItemRepository;
    /**
     *
     * @param path
     * @param fileName
     * @return 判定文件是否存在，用于检查上传了几张找照片和文件的适用操作系统
     */
    public boolean isFileExist(String path,String fileName){
        File file=new File(path+"/"+fileName);
        //System.out.println(path+"/"+fileName);
        try {
            if (!file.exists())
                return false;
            else return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
    public boolean isDataExist(String fileName){
       // System.out.println("dataExist:"+downItemRepository.existsByName(fileName));
        return downItemRepository.existsByName(fileName);
    }
    /**
     *
     * @param name
     * @return 将文件名改为 文件名_X86/_X64.拓展名 的格式
     */
    public List formatUrl(String name){
        List urls=new ArrayList();
        int index=name.lastIndexOf(".");
        String extension=name.substring(index);
        String filename=name.substring(0,index);
        urls.add(filename+"_X64"+extension);
        urls.add(filename+"_X86"+extension);
        return urls;
    }
    public List fileUrlService(String path,String kind,List url) {

        List urls = new ArrayList();
        if (isFileExist(path+ getTypeKey(kind), url.get(0).toString())) {
            Map url_64 = new HashMap();
            url_64.put("name", url.get(0).toString());
            url_64.put("fileURL", "/file/" + getTypeKey(kind) + "/" + url.get(0).toString());
            urls.add(url_64);
        }
        if (isFileExist(path + getTypeKey(kind), url.get(1).toString())) {
            Map url_86 = new HashMap();
            url_86.put("name", url.get(1).toString());
            url_86.put("fileURL", "/file/" + getTypeKey(kind) + "/" + url.get(1).toString());
            urls.add(url_86);
        }
       // System.out.println(urls.toString());
        return urls;
    }
    public String getTypeKey(String value){
        String result="";
        Type type=new Type();
        for (String res:type.map.keySet()){
            if(value.equals(type.map.get(res))){
                result=res;
            }
        }
        return result;
    }
    public String getFileRom(String filePath,String filename){
        String result="";

        return result;
    }
}
