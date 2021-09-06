package com.cos.photogram.handler;

import com.cos.photogram.handler.ex.CustomValidationException;
import com.cos.photogram.util.Script;
import com.cos.photogram.web.dto.CMRespDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e){
        //CMRespDto Script 비교
        //1. 클라이언트에게 응답할 때는 Script
        //2. Ajax통신 - CMPRespDto
        //3. Android통신 - CMRespDto
        return Script.back(e.getErrorMap().toString());
    }
}
