package com.downpu.controller;


import com.alibaba.fastjson.JSONObject;
import com.downpu.Type.Type;
import com.downpu.domain.DownItem;
import com.downpu.domain.Path;
import com.downpu.service.AdminService;
import com.downpu.service.DownItemService;
import com.downpu.service.FileService;
import com.downpu.service.MyProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.downpu.repository.DownItemRepository;
import org.springframework.data.domain.Page;

import javax.xml.ws.RequestWrapper;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by yy187 on 2017/8/20.
 */
@Controller
public class SearchController {
    @Autowired
   private DownItemRepository downItemRepository;
    @Autowired
    private DownItemService downItemService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private FileService fileService;
    @Autowired
    private MyProps myProps;
    @RequestMapping(value = "/downpu")
    public String index(){

        return "index";
    }
    @RequestMapping("/findById")
    @ResponseBody
    public String findById(@RequestParam("id")String id){
        Map item=new HashMap() ;
        DownItem downItem=(DownItem) downItemRepository.findByIdloaditem(Integer.valueOf(id));
        String kind_key=downItem.getKind();
        Type type=new Type();
        //System.out.println(type.map.get(request.getParameter("type")));
        String kind=fileService.getTypeKey(kind_key);
        List url=new ArrayList();
        List urls = fileService.formatUrl(downItem.getName());
        url=fileService.fileUrlService(myProps.getBaseDir() ,kind_key,urls);
        downItem.setKind(kind);
        //System.out.println(myProps.getBaseDir());
        item.put("url",url);
        item.put("content",downItem);
        return JSONObject.toJSON(item).toString();
    }
    /*@RequestMapping(value = "/search")
    public String getResult(ModelMap modelMap, @RequestParam("name") String name) {
        System.out.println(name);
        if (name==null||name.isEmpty()) {
            //System.out.println("null");
            return "index";
        } else {
            List<DownItem> items = downItemRepository.findByNameLike("%" + name + "%");
            List result = new ArrayList<DownItem>();
            Iterator iterator = items.iterator();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            while (iterator.hasNext()) {
                List links = new ArrayList();
                Map item = new HashMap();
                Map link_86 = new HashMap();
                Map link_64 = new HashMap();
                DownItem downItem = (DownItem) iterator.next();
                item.put("name", downItem.getName());
                item.put("postTime", format.format(downItem.getUploaddate()));
                item.put("discription", downItem.getIntro());
                int index = downItem.getUrl().lastIndexOf(".");
                String extension = downItem.getUrl().substring(index);
                String path = downItem.getUrl().substring(0, index);
                link_64.put("name", downItem.getName() + " _X86");
                link_64.put("link", "/file/" + "filename=" + downItem.getName().substring(0, downItem.getName().indexOf(".")) + "X86" + extension + "&kind=" + downItem.getKind());
                links.add(link_64);
                link_86.put("name", downItem.getName() + "_X64");
                link_86.put("link", "/file/" + "filename=" + downItem.getName().substring(0, downItem.getName().indexOf(".")) + "X64" + extension + "&kind=" + downItem.getKind());
                links.add(link_86);
                System.out.println("links" + links);
                item.put("links", links);
                result.add(item);
            }
            System.out.println(JSONArray.toJSON(result));
            modelMap.addAttribute("result", JSONArray.toJSON(result));
            return "search";
        }
    }*/

    /**
     *
     * @param fileName
     * @return 文件详情
     */
   @RequestMapping(value = "/detail")
    public String getDetail(@RequestParam("name") String fileName,ModelMap modelMap) {
       if (fileName.isEmpty() || fileName == null||fileName.equals(".")) {
           return "index";
       } else {
           List<DownItem> lists = downItemRepository.findByNameLike("%" + fileName + "%");
           //System.out.println("number:" + lists.size());
           Iterator iterator = lists.iterator();
           List results = new ArrayList();
           while (iterator.hasNext()) {
               DownItem downItem = (DownItem) iterator.next();
              // System.out.println("downname:" + downItem.getName());
               List urls = fileService.formatUrl(downItem.getName());
               List files = new ArrayList();
               Map item = new HashMap();
               Map subfiles = new HashMap();
               files=fileService.fileUrlService(myProps.getBaseDir() ,downItem.getKind(),urls);
              // System.out.println(myProps.getBaseDir());
               subfiles.put("links", files);
               Map links = new HashMap();
               links.put("links", downItem.getImage());
               item.put("name", downItem.getName());
               SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
               item.put("postTime", format.format(downItem.getUploaddate()));
               item.put("size", downItem.getSize());
               item.put("discription", downItem.getIntro());
               item.put("photos", links);
               item.put("files", subfiles);
               results.add(item);
           }
           modelMap.addAttribute("result", JSONObject.toJSON(results));
           return "search";
       }
   }

   @RequestMapping("/category")
    @ResponseBody
    public String getCategory(){
       Type type=new Type();
       List list=new ArrayList();
       for(String s:type.map.keySet()){
           list.add(s);
       }
       Map category=new HashMap();
       category.put("category",list);
       //System.out.println(JSONObject.toJSON(type.map));
       return JSONObject.toJSON(category).toString();
   }
   /**分类分页**/
   @RequestMapping("/sortByKind")
    @ResponseBody
    public String getType(@RequestParam("type")String type,@RequestParam("number")String number){
       Type type1=new Type();
    DownItem downItem=new DownItem();
       List list=new ArrayList();
       for(String s:type1.map.keySet()){
           list.add(s);
       }
       downItem.setKind(type1.map.get(type));
       Page page=downItemService.findDownItemCriteria(Integer.valueOf(number),15,downItem);
       Map result =new HashMap();
       result.put("content",page);
       Map returnResult=new HashMap();
       returnResult.put("result",result);
       return JSONObject.toJSON(returnResult).toString();
   }


}
