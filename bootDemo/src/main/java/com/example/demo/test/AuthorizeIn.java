package com.example.demo.test;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AuthorizeIn  {
    @NotNull(message = "缺少type参数")
    private String type;
    @NotBlank(message = "缺少id")
    private String clientId;
    @Length(min = 1,max = 5,message = "长度不合适")
    private String name;
    
}
