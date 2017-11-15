package com.downpu.controller;

import com.downpu.domain.DownItem;
import com.downpu.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.downpu.repository.DownItemRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by yy187 on 2017/8/20.
 */
@Controller
public class SearchController {
    @Autowired
   private DownItemRepository downItemRepository;
    @Autowired
    private CommentRepository commentRepository;
    @RequestMapping(value = "/search")
    public ModelAndView getResult(ModelMap modelMap, @RequestParam("name") String name,HttpServletResponse response){
        List<DownItem> item=downItemRepository.findByNameLike("%"+name+"%");
        if (item.size()==0){
            modelMap.addAttribute("zero","未搜索到"+name+"的结果，请换关键词继续搜索或反馈给我们");
        }
        modelMap.addAttribute("searchResult",item);
        modelMap.addAttribute("name",name);
    return new ModelAndView("search-result");
 }
   @RequestMapping(value = "/detail")
    public ModelAndView getDetail(ModelMap modelMap, @RequestParam("id") Integer id, HttpServletRequest request){
        modelMap.addAttribute("item",downItemRepository.findByIdloaditem(id));
        modelMap.addAttribute("comments",commentRepository.findByRid(id));
       HttpSession session=request.getSession();
       session.setAttribute("id",id);
       session.setAttribute("model",modelMap);
       return new ModelAndView("comment");
   }
   @RequestMapping(value = "admin/upload")
    public String upload(){
       return "上传成功";
   }
}
