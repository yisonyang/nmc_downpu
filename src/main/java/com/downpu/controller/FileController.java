package com.downpu.controller;

import com.downpu.domain.DownItem;
import com.downpu.repository.DownItemRepository;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Random;

/**
 * Created by yy187 on 2017/9/3.
 */
@Controller
public class FileController {
    HttpSession session;
    @Autowired
    private DownItemRepository downItemRepository;
    @RequestMapping(value = "/upload")
    public void upload(@RequestParam("file") MultipartFile file){
        String path="D://upload/"+downItemRepository.findByName(file.getOriginalFilename()).getKind();
        processUpload(path,file);
    }
    @RequestMapping(value = "/image")
    public void getimage(@RequestParam("image")MultipartFile file){
        String path="D://upload/image";
        processUpload(path,file);
    }
    @RequestMapping(value = "/download")
    public void download(@RequestParam("filename") String filename, @RequestParam("kind")String kind, HttpServletResponse response){
       String path="D://upload/"+kind;
        File file=new File(path+"/"+filename);
        if (!file.exists()){
            System.out.println("文件不存在");
            return;
        }
        String realname=filename.substring(filename.indexOf("-")+1);
        try {
            response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode(realname,"UTF-8"));
            FileInputStream in=new FileInputStream(path+"\\"+filename);
            OutputStream out= response.getOutputStream();
            byte buffer[]=new byte[1024];
            int len=0;
            while ((len=in.read(buffer))>0){
                out.write(buffer,0,len);
            }
            in.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @RequestMapping(value = "/uploadtext")
    public void upload_html(@Valid DownItem downItem,HttpServletRequest request){
        downItem.setName(downItem.getName());
        downItem.setIntro(downItem.getIntro());
        session=request.getSession();
        downItem.setKind(downItem.getKind());
        downItem.setUploaddate(new Date());
        downItem.setUploader(session.getAttribute("username").toString());
        downItem.setImage("D:\\upload\\image\\"+downItem.getName()+".jpg");
        downItemRepository.save(downItem);
    }
    public void processUpload(String inpath,MultipartFile file){
        String filename=file.getOriginalFilename();
        File dest=new File(inpath+"/"+filename);
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest);
        }catch (IllegalStateException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/userimage")
    public void generateImage(@RequestParam("name") String name, HttpServletResponse response, HttpServletRequest request){

        BufferedImage bi=new BufferedImage(200,40,BufferedImage.TYPE_INT_RGB);
        Graphics2D g=(Graphics2D) bi.getGraphics();
        Color c=new Color(255,255,255);
        g.setColor(c);
        g.fillRect(0,0,200,40);
        char [] ch=name.toCharArray();
        int len=ch.length,index;
        StringBuffer sb=new StringBuffer();
            g.setColor(new Color(0,0,0));
            g.setFont(new Font(ch[0]+"",10,45));
            g.setBackground(Color.GRAY);
            g.drawString(ch[0]+"",(30)+50,30);
            sb.append(ch[0]);
        try {
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
            encoder.encode(bi);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
