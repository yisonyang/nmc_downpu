package com.downpu.service;

import com.downpu.controller.UserController;
import com.downpu.domain.Idencode;
import com.downpu.domain.Users;
import com.downpu.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.sql.SQLData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by yy187 on 2017/9/6.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public Integer registerService(Users user, String repad, String idencode, HttpServletResponse response){
        if(!user.getPassword().equals(repad)){
            return 3;
        } else if(!idencode.equals(String.valueOf(Idencode.idenc))){
            return 4;
        } else if(userRepository.findByUsername(user.getUsername()).size()==0) {
            Date date=new Date();
            user.setDate(date);
            userRepository.save(user);
            Cookie cookie=new Cookie("username",user.getUsername());
            cookie.setMaxAge(604800);
            response.addCookie(cookie);
            return 1;
        }
        else {
            return 2;
        }

    }
    public Integer loginServicec(Users users,HttpServletResponse response){
        if (userRepository.findByUsername(users.getUsername()).size()==0){
            return 1;
        }else if(userRepository.findByUsernameAndPassword(users.getUsername(),users.getPassword()).size()==0){
            return 2;
        }else{
            Cookie cookie=new Cookie("username",users.getUsername());
            cookie.setMaxAge(604800);
            response.addCookie(cookie);
            return 3;
        }

    }    public Integer gene_idencode() {
        int max = 9999;
        int min = 1000;
        Random random = new Random();
        Idencode.idenc = random.nextInt(max) % (max - min + 1) + min;
        return Idencode.idenc;
    }
}

