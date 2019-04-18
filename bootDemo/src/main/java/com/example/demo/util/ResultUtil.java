package com.example.demo.util;


/**
 * Created by xuyulin on 2017/8/26.
 */
public class ResultUtil {

    public static Result getFailResult(String desc) {
        return new Result(Constants.ERROR_CODE, desc);
    }

    public static Result getFailResult() {
        return new Result(Constants.ERROR_CODE);
    }

    public static Result getSuccessResult(String desc) {
        return new Result(Constants.SUCCESS_CODE, desc);
    }

    public static Result getSuccessResult() {
        return new Result(Constants.SUCCESS_CODE);
    }

}
