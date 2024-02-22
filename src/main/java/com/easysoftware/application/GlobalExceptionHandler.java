package com.easysoftware.application;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.easysoftware.domain.common.exception.EnumValidException;
import com.easysoftware.result.MessageCode;
import com.easysoftware.result.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestControllerAdvice()
public class GlobalExceptionHandler {
    private static final Logger logger =  LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> exception(MethodArgumentNotValidException e) {
        logger.error(MessageCode.EC0002.getMsgEn());
        return Result.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0002);
    }

    @ExceptionHandler(EnumValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> exception(EnumValidException e) {
        logger.error(MessageCode.EC0002.getMsgEn());
        MessageCode messageCode = MessageCode.msgCodeMap.get(e.getMessage());
        return Result.fail(HttpStatus.BAD_REQUEST, messageCode);
    }
}
