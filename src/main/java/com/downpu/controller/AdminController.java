package com.downpu.controller;

import com.alibaba.fastjson.JSONObject;
import com.downpu.Type.Type;
import com.downpu.domain.DownItem;
import com.downpu.domain.Path;
import com.downpu.domain.Record;
import com.downpu.domain.Users;
import com.downpu.imple.DownItemServiceImpl;
import com.downpu.repository.DownItemRepository;
import com.downpu.repository.RecordRepository;
import com.downpu.repository.UserRepository;
import com.downpu.service.AdminService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.hibernate.integrator.spi.ServiceContributingIntegrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.resources.Messages_de;

import javax.jws.WebParam;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yy187 on 2017/9/11.
 */
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private DownItemRepository downItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
            private DownItemServiceImpl downItemService;
    @Autowired
            private RecordRepository recordRepository;
    HttpSession session;
    @RequestMapping("/page")
    public ModelAndView getPages(){
        return new ModelAndView("admin_login");
    }
    @RequestMapping(path = "/admin_login")
    public ModelAndView adminLogin(@RequestParam("name") String admin_name, @RequestParam("password") String admin_pw, ModelMap modelMap,HttpServletRequest request){
      int loginstate=adminService.logincheck(admin_name,admin_pw);
       switch (loginstate){
           case 1:{
                modelMap.addAttribute("error_name","用户不存在");
               return new ModelAndView("admin_login");}
           case 2:{
                modelMap.addAttribute("error_password","密码错误");
               return new ModelAndView("admin_login");}
           case 3: {
               modelMap.addAttribute("username",admin_name);
               Record record=new Record();
               record.setIp(adminService.getIpAddess(request));
               record.setName(admin_name);
               record.setDate(new Date());
               recordRepository.save(record);
               session=request.getSession();
               session.setAttribute("username",admin_name);
               return new ModelAndView("list");
           }
       }
        return new ModelAndView("admin/login");
    }
    @RequestMapping("/admin")
    public String adminIndex(HttpServletRequest request)
    {
        session=request.getSession();
        String username=(String) session.getAttribute("username");
        if (username==null){
            return "admin_login";
        }else {
            return "admin";
        }
    }
    @RequestMapping("/users")
    public ModelAndView allUsers(ModelMap modelMap,HttpServletRequest request){
        List<Users> alluser=userRepository.findAll();
        session=request.getSession();
        modelMap.addAttribute("username",session.getAttribute("username"));
        modelMap.addAttribute("users",alluser);
        return new ModelAndView("admin/tables");
    }
    @RequestMapping("/del")
    public ModelAndView delete(@RequestParam("id")Integer id,ModelMap modelMap,HttpServletRequest request){
        List<Users> alluser=userRepository.findAll();
        modelMap.addAttribute("users",alluser);
        session=request.getSession();
        modelMap.addAttribute("username",session.getAttribute("username"));
        userRepository.deleteUsersByUserid(id);
        return new ModelAndView("admin/tables");
    }
    @RequestMapping("/reset")
    public ModelAndView resetpw(@RequestParam("id") Integer id, ModelMap modelMap, HttpServletRequest request){
        Users users=userRepository.findByUserid(id);
        users.setPassword("123456");
        session=request.getSession();
        modelMap.addAttribute("username",session.getAttribute("username"));
        userRepository.save(users);
        List<Users> alluser=userRepository.findAll();
        modelMap.addAttribute("users",alluser);
        return new ModelAndView("admin/tables");
    }
    @RequestMapping("/getall")
    public ModelAndView findall(ModelMap modelMap, HttpServletRequest request){
        List<DownItem> items=downItemRepository.findAll();
        modelMap.addAttribute("items",items);
        session=request.getSession();
        modelMap.addAttribute("username",session.getAttribute("username"));
        return new ModelAndView("admin/tables");
    }
    @RequestMapping(path = "/update")
    public void update(DownItem downItem){
        downItemRepository.save(downItem);
    }
    @RequestMapping(path= "/resources")
    public ModelAndView getResources(HttpServletRequest request,ModelMap modelMap){
        session=request.getSession();
        modelMap.addAttribute("username",session.getAttribute("username"));
        modelMap.addAttribute("resources",downItemRepository.findAll());
        return new ModelAndView("/admin/resources");
    }
    @RequestMapping("/delitem")
    @ResponseBody
    public String delItem(HttpServletRequest request, @RequestParam("id")Integer id, ModelMap modelMap){
        session=request.getSession();
        modelMap.addAttribute("username",session.getAttribute("username"));
        downItemRepository.deleteDownItemByIdloaditem(id);
        modelMap.addAttribute("resources",downItemRepository.findAll());
        Map data=new HashMap();
        data.put("data",true);
        return JSONObject.toJSON(data).toString();
    }
    @RequestMapping(path = "/edit")
    @ResponseBody
    public String edit(HttpServletRequest request,@RequestParam("id")String id,@RequestParam("name")String name,@RequestParam("content")String intro,@RequestParam("sort")String type){
        session=request.getSession();
        session.setAttribute("id",id);
        DownItem item=(DownItem) downItemRepository.findByIdloaditem(Integer.valueOf(id));
        item.setName(name);
        item.setIntro(intro);
        //System.out.println("type:"+type);
        Type type1=new Type();
        item.setKind(type1.map.get(type));
        downItemRepository.save(item);
        Map map=new HashMap();
        map.put("data",true);
        return JSONObject.toJSON(map).toString();
    }
    @RequestMapping(path = "/edittext")
    public ModelAndView edittext(@RequestParam("fileName")String name,@RequestParam("kind") String kind,@RequestParam("intro")String intro,HttpServletRequest request,ModelMap modelMap){
        Integer id=(Integer) session.getAttribute("id");
               DownItem downItem=(DownItem) downItemRepository.findByIdloaditem(id);
               downItem.setName(name);
               downItem.setKind(kind);
               downItem.setIntro(intro);
               downItemRepository.save(downItem);
            session=request.getSession();
            modelMap.addAttribute("username",session.getAttribute("username"));
            modelMap.addAttribute("resources",downItemRepository.findAll());
            return new ModelAndView("/admin/resources");

    }
    @RequestMapping("/search_a")
    @ResponseBody
    public String getResult(@RequestParam("search")String name){
        List<DownItem> list=downItemRepository.findByNameLike("%"+name+"%");
        Map map=new HashMap();
        map.put("content",list);
        map.put("count",list.size());
        System.out.println(JSONObject.toJSON(map));
        return JSONObject.toJSON(map).toString();
    }
    @RequestMapping("/list")
    public String list(HttpServletRequest request){
        session=request.getSession();
        String username=(String) session.getAttribute("username");
        if (username==null){
            return "admin_login";
        }else {
            return "list";
        }
    }
    @RequestMapping("/getLists")
    @ResponseBody
    public Page getList(@RequestParam("pageNumber")String pageNumber){
            int pagenumber=Integer.valueOf(pageNumber);
            int pageSize=15;
            Page<DownItem> downItem=this.downItemService.findDownItemNoCriteria(pagenumber,pageSize);
        Map result =new HashMap();
        result.put("content",downItem.getContent());
            return downItem;
    }
    @RequestMapping(path = "/setPath")
    public void setPath(@RequestParam("path") String path,@RequestParam("type") String type) {
        switch (type){
            case "file": {
                Path.filePath = path;
                break;
            }
            case "image":{
                Path.path=path;
                break;

            }        }
    }
}
