package com.downpu.service;

import com.downpu.domain.Admin;
import com.downpu.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yy187 on 2017/9/11.
 */
@Service
public class AdminService {
    @Autowired
    /**登录验证**/
    private AdminRepository adminRepository;
    public int logincheck(String username, String password){
        List<Admin> admins=adminRepository.findByName(username);
        if (admins.size()==0){
            return 1;//用户名不存在
        }else if (adminRepository.findByNameAndPassword(username,password).size()==0){
            return 2;//密码错误
        }else{
           // session=request.getSession();
           // session.setAttribute("admin_Name",username);
            return 3;
        }
    }
    /**记录管理员登录行为**/
   public String getIpAddess(HttpServletRequest request){
        String ip=request.getHeader("x-forwarded-for");
        if (ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("Proxy-Client-IP");
        }
       if (ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
           ip = request.getHeader("WL-Proxy-Client-IP");
       }
       if (ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
           ip = request.getHeader("HTTP_CLIENT_IP");
       }
       if (ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
           ip = request.getHeader("HTTP_X_FORWARDED_FOR");
       }
       if (ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
           ip = request.getRemoteAddr();
       }
       return ip;
   }
   public String getKey(Map map,String value){
       Set set=map.entrySet();
       String val="";
       Iterator iterator=set.iterator();
       while (iterator.hasNext()){
           Map.Entry entry=(Map.Entry) iterator.next();
           if (entry.getValue().equals(value));
           val=entry.getKey().toString();
       }
       return val;
   }
}
