package com.jjyu.controller;


import com.jjyu.service.UserOperationService;
import com.jjyu.utils.ResultForFront;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "prManage/userOperation", tags = {"记录用户操作"})
@Slf4j
@RestController
@RequestMapping("prManage/userOperation")
public class UserOperationController {
    @Autowired
    private UserOperationService userOperationService;


    @ApiOperation(value = "获取所有操作记录", notes = "listAllOperation")
    @GetMapping("/listAllOperation")
    public ResultForFront listAllOperation() {
        return ResultForFront.succ(userOperationService.list());
    }


}
