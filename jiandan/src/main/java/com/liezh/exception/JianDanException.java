package com.liezh.exception;

/**
 * Created by Administrator on 2018/2/19.
 */
public class JianDanException extends RuntimeException{

    public JianDanException(String message) {
        super(message);
    }

    public JianDanException(String message, Throwable cause) {
        super(message, cause);
    }

}
