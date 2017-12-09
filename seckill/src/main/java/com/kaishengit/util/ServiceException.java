package com.kaishengit.util;

/**
 * 业务异常
 * @author 刘帅
 */
public class ServiceException extends RuntimeException {

    public ServiceException(){}

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable th) {
        super(th);
    }

    public ServiceException(Throwable th,String message) {
        super(message,th);
    }
}
