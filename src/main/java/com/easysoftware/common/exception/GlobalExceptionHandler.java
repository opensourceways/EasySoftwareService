package com.easysoftware.common.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger =  LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> exception(MethodArgumentNotValidException e) {
        logger.error(MessageCode.EC0002.getMsgEn());
        return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0002);
    }

    @ExceptionHandler(ParamErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> exception(ParamErrorException e) {
        logger.error(e.getMessage());
        return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0002, e.getMessage());
    }

    @ExceptionHandler(EnumValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> exception(EnumValidException e) {
        logger.error(MessageCode.EC0002.getMsgEn());
        return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0002);
    }

    @ExceptionHandler(AppPkgIconException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> exception(AppPkgIconException e) {
        logger.error(MessageCode.EC0009.getMsgEn());
        return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0009);
    }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> exception(AuthException e) {
        logger.error(e.getMessage());
        return ResultUtil.fail(HttpStatus.UNAUTHORIZED, MessageCode.EC00012, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> exception(Exception e) {
        logger.error(e.getMessage());
        return ResultUtil.fail(HttpStatus.INTERNAL_SERVER_ERROR, MessageCode.ES0001);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> exception(RuntimeException e) {
        return ResultUtil.fail(HttpStatus.INTERNAL_SERVER_ERROR, MessageCode.ES0001, e.getMessage());
    }
}
