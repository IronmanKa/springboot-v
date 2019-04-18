package com.example.demo.annotation;

import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {
    //路径中版本的前缀， 这里用 /v[1-9] 的形式
    private final static Pattern VERSION_PERFIX_PATTERN = Pattern.compile("v(\\d+)/");
    private int apiVersion;
    public ApiVersionCondition(int apiVersion){
        this.apiVersion = apiVersion;
    }

    public void setApiVersion(int apiVersion) {
        this.apiVersion = apiVersion;
    }

    public int getApiVersion() {
        return apiVersion;
    }

    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        //采用最后定义优先原则，则方法上的定义覆盖类上的定义
        return new ApiVersionCondition(other.getApiVersion());
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        Matcher m = VERSION_PERFIX_PATTERN.matcher(request.getRequestURI());
        if (m.find()){
            Integer version = Integer.valueOf(m.group(1));
            if (version >= this.apiVersion){
                return this;
            }
        }
        return null;
    }

    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        //优先匹配最新的版本号
        return other.getApiVersion() - this.apiVersion;
    }
}
