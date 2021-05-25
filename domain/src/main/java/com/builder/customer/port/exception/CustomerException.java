package com.builder.customer.port.exception;

public class CustomerException extends Exception {
    public CustomerException(String errMessage, Throwable err) {
        super(errMessage, err);
    }
}
