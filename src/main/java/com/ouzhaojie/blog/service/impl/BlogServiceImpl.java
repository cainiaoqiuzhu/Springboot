package com.ouzhaojie.blog.service.impl;

import com.ouzhaojie.blog.entity.Blog;
import com.ouzhaojie.blog.mapper.BlogMapper;
import com.ouzhaojie.blog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2022-03-22
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
