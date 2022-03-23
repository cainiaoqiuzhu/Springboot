package com.ouzhaojie.blog.shiro;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountProfile implements Serializable {

    // 封装用户信息，以加密
    private Long id;

    private String username;

    private String avatar;

    private String email;

}
