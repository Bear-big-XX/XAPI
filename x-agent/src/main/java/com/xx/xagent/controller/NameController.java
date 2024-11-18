package com.xx.xagent.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.xx.xagent.model.User;
import com.xx.xagent.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
/*
@RestController
@RequestMapping("/name")
@Slf4j
public class NameController {
    @GetMapping("/")
    public String getNameByGet(String name){
        return "GET 你的名字是" + name;
    }

    @PostMapping("/")
    public String getNameByPost(@RequestParam String name, HttpServletRequest request){

        return "POST 你的名字是" + name;
    }

    @PostMapping("/12")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request)
    {
        // 从请求头中获取参数
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String body = request.getHeader("body");
        String sign = request.getHeader("sign");
        // 从请求头中获取名为“secretKey”的值
        // String secretKey = request.getHeader("secretKey");


        // 校验accessKey
        if(!accessKey.equals("123"))
        {
            throw new RuntimeException("无权限");
        }

        // 校验随机数
        if(Long.parseLong(nonce) > 10000){
            throw new RuntimeException("无权限");
        }
        log.info("传递随机数是：" + nonce);

        // 校验请求体
        if(!JSONUtil.toJsonStr(user).equals(body)){
            throw new RuntimeException("请求体校验错误！");
        }
        log.info("请求体是：" + JSONUtil.toJsonStr(user));

        // 请求签名
        if(!SignUtils.genSign(body,"456").equals(sign)){
            throw new RuntimeException("签名校验错误！");
        }
        log.info("服务端签名是：" + SignUtils.genSign(body,"456"));
        log.info("传递签名是：" + sign);

        // 权限校验通过
        return "POST 用户名字是" + user.getUsername();
    }
}
*/