package com.downpu.controller;

import com.downpu.domain.DownItem;
import com.downpu.domain.Users;
import com.downpu.repository.DownItemRepository;
import com.downpu.repository.UserRepository;
import com.downpu.service.AdminService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import java.util.Date;
import java.util.List;

/**
 * Created by yy187 on 2017/9/11.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private DownItemRepository downItemRepository;
    @Autowired
    private UserRepository userRepository;
    HttpSession session;
    @RequestMapping("/page")
    public ModelAndView getPages(){
        return new ModelAndView("admin/login");
    }
    @RequestMapping(path = "/login")
    public ModelAndView adminLogin(@RequestParam("name") String admin_name, @RequestParam("password") String admin_pw, ModelMap modelMap,HttpServletRequest request){
      int loginstate=adminService.logincheck(admin_name,admin_pw);
       switch (loginstate){
           case 1:{
                modelMap.addAttribute("error_name","用户不存在");
               return new ModelAndView("admin/login");}
           case 2:{
                modelMap.addAttribute("error_password","密码错误");
               return new ModelAndView("admin/login");}
           case 3: {
               modelMap.addAttribute("username",admin_name);
               session=request.getSession();
               session.setAttribute("username",admin_name);
               DownItem downItem=new DownItem();
               modelMap.addAttribute("file_Item",downItem);
               return new ModelAndView("admin/file-management");
           }
       }
        return new ModelAndView("admin/login");
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
    @RequestMapping(path = "/delitem")
    public ModelAndView delItem(HttpServletRequest request, @RequestParam("id")Integer id, ModelMap modelMap){
        session=request.getSession();
        modelMap.addAttribute("username",session.getAttribute("username"));
        downItemRepository.deleteDownItemByIdloaditem(id);
        modelMap.addAttribute("resources",downItemRepository.findAll());
        return new ModelAndView("/admin/resources");
    }
    @RequestMapping(path = "/edit")
    public ModelAndView edit(HttpServletRequest request,ModelMap modelMap,@RequestParam("id")Integer id){
        session=request.getSession();
        session.setAttribute("id",id);
        DownItem item=new DownItem();
        modelMap.addAttribute("item",downItemRepository.findByIdloaditem(id));
        System.out.print(downItemRepository.findByIdloaditem(id));
        modelMap.addAttribute("file_Item",item);
        modelMap.addAttribute("username",session.getAttribute("username"));
        return new ModelAndView("/admin/edit");
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
}
