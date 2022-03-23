package com.ouzhaojie.blog.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ouzhaojie.blog.common.lang.Result;
import com.ouzhaojie.blog.entity.Blog;
import com.ouzhaojie.blog.service.BlogService;
import com.ouzhaojie.blog.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2022-03-22
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){

        if(currentPage == null || currentPage < 1) currentPage = 1;
        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.succ(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") long id){
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已删除");
        return Result.succ(blog);
    }

    @RequiresAuthentication // 需要登录后才能访问
    @PostMapping("/blog/edit")
    // 编辑和添加师同一体的
    public Result edit(@Validated @RequestBody Blog blog){
        System.out.println(blog.toString());
        Blog temp = null;
        if(blog.getId() != null){
            System.out.println("hahahahaha");
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章(当前的blog的用户id应该是等于shiro里的用户di(就是现在登录的那个用户))
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtils.getProfile().getId().longValue(),"没有编辑权限");
        }else{
            // 添加
            temp = new Blog();
            temp.setUserId(ShiroUtils.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        return Result.succ(200,"操作成功", null);
    }

    @RequiresAuthentication // 必须登录了才能操作
    @GetMapping("/blog/del/{blog_id}")
    public Result del(@PathVariable("blog_id") long blog_id){

        if(blogService.removeById(blog_id)){
            return Result.succ(200, "删除成功", null);
        }else{
            return Result.fail("删除失败", null);
        }
    }

}
