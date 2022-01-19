package com.jjyu.controller;


import com.jjyu.service.DataService;
import com.jjyu.utils.ResultForFront;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController()
@RequestMapping("data")
@Slf4j
public class DataController {
    @Autowired
    private DataService dataService;

    @PostMapping("/synData")
    public ResultForFront synData(@RequestParam("maxPRNum")String maxPRNum,
                                  @RequestParam("ownerName")String ownerName,
                                  @RequestParam("repoName")String repoName){
        log.info("=============syndata执行开始");
        try {
            dataService.synData(maxPRNum,ownerName,repoName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("=============syndata执行结束");
        return ResultForFront.ok("成功执行结束");

    }
}
