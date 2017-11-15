package com.downpu.controller;

import com.downpu.repository.UserRepository;
import com.downpu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.downpu.domain.Users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    HttpSession session;
 @RequestMapping("/user")
 public ModelAndView getlogin(Model model)
 {
      Users users=new Users();
      model.addAttribute("user",users);
      model.addAttribute("idencode",userService.gene_idencode());
     return new ModelAndView("/login");
 }
@RequestMapping("/register")
    public ModelAndView register(Users user, @RequestParam("repawd")String repawd, @RequestParam("idencode")String idencode, HttpServletResponse response, Model model, HttpServletRequest request){
     Integer register_state=userService.registerService(user,repawd,idencode,response);
     session=request.getSession();
    Users users=new Users();
    model.addAttribute("user",users);
     switch (register_state){
         case 1:{
             try {
                 response.sendRedirect("/detail?id="+session.getAttribute("id"));
             } catch (Exception e){
             e.printStackTrace();
              }
         }
         case 2:{
             model.addAttribute("error_name","用户名已存在，请更改用户名");
             model.addAttribute("idencode",userService.gene_idencode());
             model.addAttribute("idencode",userService.gene_idencode());
             return new ModelAndView("/login");
         }
         case 3:{
             model.addAttribute("error_password","两次输入的密码不一致");
             model.addAttribute("idencode",userService.gene_idencode());
             model.addAttribute("idencode",userService.gene_idencode());
             return new ModelAndView("/login");
         }
         case 4:{
             model.addAttribute("error_idencode","验证码输入错误");
             model.addAttribute("idencode",userService.gene_idencode());
             return new ModelAndView("/login");
         }
     }
    return new ModelAndView("../index");
 }
 @RequestMapping("/login")
    public ModelAndView login(Users users,Model model,HttpServletResponse response){
        Integer login_state=userService.loginServicec(users,response);
        Users user=new Users();
        model.addAttribute("user",user);
     switch (login_state){
         case 1:{
             model.addAttribute("error_null","用户不存在");
             model.addAttribute("idencode",userService.gene_idencode());
             return new ModelAndView("/login");
         }case 2:{
             model.addAttribute("error_incorrect","密码错误");
             model.addAttribute("idencode",userService.gene_idencode());
             return new ModelAndView("/login");
         }case 3:{
             try {
                 response.sendRedirect("index.html");
             }catch (Exception e){
                 e.printStackTrace();
             }
         }
     }
     return new ModelAndView("/login");
 }
}
