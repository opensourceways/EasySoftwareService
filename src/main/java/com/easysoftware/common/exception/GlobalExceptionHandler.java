/* Copyright (c) 2024 openEuler Community
 EasySoftware is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

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
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotLoginException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Logger instance for logging exceptions.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles exceptions of type MethodArgumentNotValidException.
     *
     * @param e The MethodArgumentNotValidException to handle
     * @return ResponseEntity containing details about the exception
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> exception(final MethodArgumentNotValidException e) {
        LOGGER.error(MessageCode.EC0002.getMsgEn());
        return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0002);
    }

    /**
     * Handles exceptions of type ParamErrorException.
     *
     * @param e The ParamErrorException to handle
     * @return ResponseEntity containing details about the exception
     */
    @ExceptionHandler(ParamErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> exception(final ParamErrorException e) {
        LOGGER.error(e.getMessage());
        return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0002);
    }

    /**
     * Handles exceptions of type EnumValidException.
     *
     * @param e The EnumValidException to handle
     * @return ResponseEntity containing details about the exception
     */
    @ExceptionHandler(EnumValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> exception(final EnumValidException e) {
        LOGGER.error(MessageCode.EC0002.getMsgEn());
        return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0002);
    }

    /**
     * Handles exceptions of type EnumValidException.
     *
     * @param e The EnumValidException to handle
     * @return ResponseEntity containing details about the exception
     */
    @ExceptionHandler(NoneResException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> exception(final NoneResException e) {
        LOGGER.error(MessageCode.EC0002.getMsgEn());
        return ResultUtil.fail(HttpStatus.NOT_FOUND, MessageCode.EC0009.getMsgEn());
    }

    /**
     * Handles exceptions of type HttpRequestException.
     *
     * @param e The HttpRequestException to handle
     * @return ResponseEntity containing details about the exception
     */
    @ExceptionHandler(HttpRequestException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> exception(final HttpRequestException e) {
        LOGGER.error(e.getMessage());
        return ResultUtil.fail(HttpStatus.NOT_FOUND, MessageCode.EC0001);
    }

    /**
     * Handles exceptions of type NotPermissionException.
     *
     * @param e The NotPermissionException to handle
     * @return ResponseEntity containing details about the exception
     */
    @ExceptionHandler(NotPermissionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> exception(final NotPermissionException e) {
        LOGGER.error(e.getMessage());
        return ResultUtil.fail(HttpStatus.FORBIDDEN, MessageCode.EC00019);
    }

    /**
     * Handles exceptions of type NotLoginException.
     *
     * @param e The NotLoginException to handle
     * @return ResponseEntity containing details about the exception
     */
    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> exception(final NotLoginException e) {
        LOGGER.error(e.getMessage());
        return ResultUtil.fail(HttpStatus.FORBIDDEN, MessageCode.EC00018);
    }

    /**
     * Handles exceptions of type AppPkgIconException.
     *
     * @param e The AppPkgIconException to handle
     * @return ResponseEntity containing details about the exception
     */
    @ExceptionHandler(AppPkgIconException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> exception(final AppPkgIconException e) {
        LOGGER.error(MessageCode.EC0009.getMsgEn());
        return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0009);
    }

    /**
     * Handles exceptions of type UpdateException.
     *
     * @param e The UpdateException to handle
     * @return ResponseEntity containing details about the exception
     */
    @ExceptionHandler(UpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> exception(final UpdateException e) {
        LOGGER.error(e.getMessage());
        return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0004, e.getMessage());
    }

    /**
     * Handles exceptions of type InsertException.
     *
     * @param e The InsertExceptionInsertException to handle
     * @return ResponseEntity containing details about the exception
     */
    @ExceptionHandler(InsertException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> exception(final InsertException e) {
        LOGGER.error(e.getMessage());
        return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0006, e.getMessage());
    }

    /**
     * Handles general exceptions.
     *
     * @param e The Exception to handle
     * @return ResponseEntity containing details about the exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> exception(final Exception e) {
        LOGGER.error(e.getMessage());
        return ResultUtil.fail(HttpStatus.INTERNAL_SERVER_ERROR, MessageCode.ES0001);
    }

    /**
     * Handles runtime exceptions.
     *
     * @param e The RuntimeException to handle
     * @return ResponseEntity containing details about the exception
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> exception(final RuntimeException e) {
        return ResultUtil.fail(HttpStatus.INTERNAL_SERVER_ERROR, MessageCode.ES0001);
    }
}
