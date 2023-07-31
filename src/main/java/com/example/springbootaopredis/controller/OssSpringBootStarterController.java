package com.example.springbootaopredis.controller;

import com.amazonaws.services.s3.model.Bucket;
import com.example.springbootaopredis.util.Result;
import com.zhongcg.oss.core.OssTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @title OssSpringBootStarterController
 * @Author zhongcg
 * @Description oss测试
 * @Date 2023/7/31 14:51
 **/
@RestController
@RequestMapping(path = "/ossTest")
public class OssSpringBootStarterController {

    @Autowired
    private OssTemplate ossTemplate;

    @GetMapping(path = "getAllBuckets")
    public Result<List<Bucket>> getAllBuckets(){
        List<Bucket> allBuckets = ossTemplate.getAllBuckets();

        return Result.success(allBuckets);
    }
}
