package me.jcala.blog.controller.admin;

import me.jcala.blog.domain.BlogView;
import me.jcala.blog.service.AdminBlogSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jcala on 2016/7/17
 */
@Controller
@RequestMapping("/admin")
public class AdminBlogCtrl {
    @Autowired
    private AdminBlogSer adminBlogSer;
    @GetMapping("/blogAdd")
    public String newBlogBefore() {
        return "admin/blog_add";
    }
    @GetMapping("/update{id:\\d+}")
    public String blogModify(@PathVariable int id,Model model) {
        BlogView blogView=adminBlogSer.getBlogByVid(id);
        if (blogView==null){
            return "error";
        }else {
            blogView.setVid(id);
            model.addAttribute("blog",blogView);
            return "admin/blog_modify";
        }
    }
    @PostMapping("/post")
    public String post(BlogView view,Model model) {
        boolean result=true;
        if (result){
            model.addAttribute("targetUrl","/admin/blogList/1");
            model.addAttribute("result",1);
        }else {
            model.addAttribute("targetUrl","/admin/blog_add");
            model.addAttribute("result",0);
        }
        return "admin/result";

    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id,BlogView view,Model model) {
        view.setVid(id);
        boolean result= adminBlogSer.updateBlog(view);
        if (result){
            model.addAttribute("targetUrl","/admin/blogList/1");
            model.addAttribute("result",1);
        }else {
            model.addAttribute("targetUrl","/admin/update/"+id);
            model.addAttribute("result",0);
        }
        return "admin/result";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {

        return "redirect:/admin/blogList/1";
    }
    @GetMapping("/blogList/{id}")
    public String blogList(@PathVariable int id, Model model) {
        model.addAttribute("currentPageId",id);
        model.addAttribute("pageNum",adminBlogSer.getPageNum());
        model.addAttribute("blogList",adminBlogSer.getBlogPage(id));
        return "admin/blog_list";
    }
}