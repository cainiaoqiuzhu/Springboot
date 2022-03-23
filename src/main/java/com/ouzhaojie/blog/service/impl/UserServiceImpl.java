package com.ouzhaojie.blog.service.impl;

import com.ouzhaojie.blog.entity.User;
import com.ouzhaojie.blog.mapper.UserMapper;
import com.ouzhaojie.blog.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
