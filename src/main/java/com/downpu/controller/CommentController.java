package com.downpu.controller;

import com.downpu.domain.Comment;
import com.downpu.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.client.support.HttpAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by yy187 on 2017/8/30.
 */
@Controller
public class CommentController {
    HttpSession session=null;
    @Autowired
    private CommentRepository commentRepository;
    @RequestMapping("/comment")
    public ModelAndView getComment(@RequestParam("relateid") String id, ModelMap modelMap){
        Comment comment=new Comment();
        List<Comment> commentList=commentRepository.findById(Integer.valueOf(id));
        if (commentList.size()!=0){
            Collections.reverse(commentList);
            Iterator<Comment> ll=commentList.iterator();
            while (ll.hasNext()){
                Comment c=ll.next();
                System.out.println(c.getLoadDate());
            }
        }
        modelMap.addAttribute("comment",commentList);
        modelMap.addAttribute("commentObject",comment);
        return new ModelAndView("comment");
    }
    @RequestMapping("/setComment")
    public void setComment(@RequestParam("usercomment")String usercomment, @RequestParam("relateid")String relateid, HttpServletRequest request, HttpServletResponse response){
           session=request.getSession();
            Comment comment=new Comment();
            String username="";
            Date date=new Date();
            Cookie []cookies=request.getCookies();
              int flag=0;
            for (int i=0;i<cookies.length;i++){
                Cookie cookie1=cookies[i];
                    if (cookie1.getName().equals("username")) {
                        flag=flag+1;
                        username = cookie1.getValue();
                        comment.setLoadDate(date);
                        comment.setRid(Integer.valueOf(relateid));
                        comment.setCommenttext(usercomment);
                        comment.setUsername(username);
                        commentRepository.save(comment);
                        try {
                            ModelMap modelMap=(ModelMap) session.getAttribute("model");
                            System.out.print(session.getAttribute("id"));
                            modelMap.addAttribute("item",commentRepository.findByRid(Integer.valueOf(session.getAttribute("id").toString())));
                            try {
                                response.sendRedirect("/detail?id="+session.getAttribute("id"));
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                  }
                  if (flag==0){
                    try {
                        response.sendRedirect("/user");
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
            }
    }
}