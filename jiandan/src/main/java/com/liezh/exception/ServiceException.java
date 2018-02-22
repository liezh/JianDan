package com.liezh.exception;

/**
 * Created by Administrator on 2018/2/19.
 */
public class ServiceException extends JianDanException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
