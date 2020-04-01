package com.itcast.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *该类是为了将手机验证码存入redis中进行判别
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultEntity<T> {

    private String result;
    private String message;
    private T data;

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    public static final String NO_MSG = "NO_MSG";
    public static final String NO_DATA = "NO_DATA";

    public static ResultEntity<String> successNoData() {
        return new ResultEntity<String>(SUCCESS, NO_MSG, NO_DATA);
    }

    public static <T> ResultEntity<T> successWithData(T data) {
        return new ResultEntity<T>(SUCCESS, NO_MSG, data);
    }

    public static <T> ResultEntity<T> failed(String message) {
        return new ResultEntity<T>(FAILED, message, null);
    }

}
