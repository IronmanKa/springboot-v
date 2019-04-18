package com.example.demo.test;

import com.example.demo.annotation.ApiVersion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/dd")
public class HelloController {
    
    @ApiVersion(1)
    @RequestMapping("{version}/dd")
    public String ss() {
        return "sssssss";
    }
    
    @ApiVersion(2)
    @RequestMapping("{version}/dd")
    public String sss(){
        return "ddddddd" + "ssdfd";
    }
    
    @RequestMapping("index.html")
    public  String index(){
        return "index";
    }
    
}
