package com.downpu.controller;

import com.alibaba.fastjson.JSONObject;
import com.downpu.Type.Type;
import com.downpu.domain.DownItem;
import com.downpu.domain.Path;
import com.downpu.repository.DownItemRepository;
import com.downpu.service.FileService;
import com.downpu.service.MyProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.*;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yy187 on 2017/9/3.
 */
@Controller
public class FileController {
    HttpSession session;
    @Autowired
    private DownItemRepository downItemRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private MyProps myProps;
    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "file",required = false) MultipartFile[] file,HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        try {
            request.setCharacterEncoding("utf-8");
            String name = request.getParameter("appName");
            List images = new ArrayList();
            int number = 0;
            if (fileService.isDataExist(name)) {
                jsonObject.put("result", "资源已经存在");
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                for (MultipartFile multipartFile : file) {
                    if (multipartFile.isEmpty()) {
                        continue;
                    } else {
                        number++;
                        String fname = multipartFile.getOriginalFilename();
                        // int index=fname.lastIndexOf(".");
                        //String extension=fname.substring(index);
                        String imagename = "";
                        imagename = name + "_" + number + ".png";
                        processUpload(myProps.getImageDir(), multipartFile, imagename);
                        stringBuilder.append("image" + "/" + imagename + ",");
                    }
                }
                DownItem downItem = new DownItem();
                downItem.setName(request.getParameter("appName"));
                downItem.setIntro(request.getParameter("description").replace("/\n|\r\n/g", "<br>"));
                //System.out.println(request.getParameter("type")+":type");
                Type type = new Type();
                //System.out.println(type.map.get(request.getParameter("type")));
                downItem.setKind(type.map.get(request.getParameter("type")).toString());
                //System.out.println("type:"+request.getParameter("type"));
                downItem.setUploader("admin");
                //downItem.setSize(request.getParameter("size"));
                downItem.setImage(stringBuilder.toString());
                downItem.setUploaddate(new Date());
                downItem.setUrl("/file" + "/" + downItem.getKind() + "/" + name);
                // System.out.println(downItem.getName()+downItem.getIntro());
                downItemRepository.save(downItem);
                jsonObject.put("result", "success");
            }
            }catch(Exception e){
            jsonObject.put("result","请勿留空");
                e.printStackTrace();
            }
        return jsonObject.toJSONString();
    }
/*    @RequestMapping(value = "/download")
    public void download(@RequestParam("filename") String filename, @RequestParam(value = "kind",required = false)String kind, HttpServletResponse response){
        File file=new File(path+"/"+filename);
        System.out.println("download:"+path+"/"+filename);
        if (!file.exists()){
            System.out.println("文件不存在");
            return;
        }
        String realname=filename.substring(filename.indexOf("-")+1);
        try {
            response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode(realname,"UTF-8"));
            FileInputStream in=new FileInputStream(path+"/"+filename);
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
    }*/
   /* @RequestMapping(value = "/uploadtext")
    public void upload_html(@Valid DownItem downItem,HttpServletRequest request){
        downItem.setName(downItem.getName());
        downItem.setIntro(downItem.getIntro());
        session=request.getSession();
        downItem.setKind(downItem.getKind());
        downItem.setUploaddate(new Date());
        downItem.setUploader(session.getAttribute("username").toString());
        downItem.setImage();
        downItemRepository.save(downItem);
    }*/
   /**处理文件上传**/
    public void processUpload(String inpath,MultipartFile file,String name){
       // System.out.println(inpath);
       // String filename=file.getOriginalFilename();
        File dest=new File(inpath+"/"+name);
       // System.out.println("文件已上传，上传目录是："+inpath+"文件名是"+name);
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
/*    @RequestMapping(value = "/userimage")
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

    }*/
}
