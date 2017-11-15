package com.downpu.service;

import com.downpu.domain.Admin;
import com.downpu.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by yy187 on 2017/9/11.
 */
@Service
public class AdminService {
    @Autowired
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
}
