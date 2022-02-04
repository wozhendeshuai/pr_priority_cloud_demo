package com.jjyu.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(value = "project/user", tags = {"用户controller"})
@Slf4j
@RestController
@RequestMapping("project/user")
public class UserController {
    //1.用户基础信息的修改
    //2.用户权限查询
}
