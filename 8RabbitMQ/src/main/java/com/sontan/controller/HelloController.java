package com.sontan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//控制器类
@RestController  //使得该类变成控制器类
public class HelloController {

    //@GetMapping等价于@RequestMapping(method = RequestMethod.GET),将HTTP Get请求映射到特定的处理方法上
    @GetMapping("/hello")  //使得请求路径和处理函数关联起来
    public String helloSpringboot() {  //请求处理函数
        return "Hello,Spring boot!";
    }
}
