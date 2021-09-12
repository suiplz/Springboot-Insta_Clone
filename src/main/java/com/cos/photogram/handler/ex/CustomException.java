package com.cos.photogram.handler.ex;

import com.cos.photogram.util.Script;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;


public class CustomException extends RuntimeException{

    //객체를 구분할 때
    private static final long serialVersionUID = 1L;


    public CustomException(String message) {
        super(message);
    }

    @ExceptionHandler(CustomException.class)
    public String Exception(CustomException e) {

        return Script.back(e.getMessage());
    }
}
