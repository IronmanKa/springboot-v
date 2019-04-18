/**
 * @Title: Result
 * @Package cn.com.artemis.biz.steinsggs.common
 * @Description: TODO
 * @author yulin.xu@downjoy.com
 * @date 2017/7/20 16:05
 * @version V1.0
 */
package com.example.demo.util;


import java.util.HashMap;
import java.util.Map;

/**
 *
 *@ClassName: Result
 * @Description: TODO
 * @author yulin.xu@downjoy.com
 * @date 2017/7/20 16:05
 *
 */
public class Result {

    private int statusCode = Constants.ERROR_CODE;

    private String desc;

    private Map<String, Object> data = new HashMap<String, Object>();

    public Result(int statusCode) {
        this.statusCode = statusCode;
    }

    public Result(int statusCode, String desc) {
        this.statusCode = statusCode;
        this.desc = desc;
    }

   public Result put(String name, Object data) {
        this.data.put(name, data);
        return this;
   }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getData() {
        return data;
    }
}
