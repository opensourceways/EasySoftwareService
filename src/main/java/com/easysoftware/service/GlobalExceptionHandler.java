package com.easysoftware.service;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.easysoftware.entity.vo.HttpResult;
import com.easysoftware.exception.ParamErrorException;
// import com.easysoftware.util.ResponseResult;
// import com.easysoftware.util.ResultEnum;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;


@Slf4j
@RestControllerAdvice()
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        if (!result.hasErrors()) {
            return HttpResult.fail(HttpStatus.BAD_REQUEST.value(), "请求错误", null);
        }
        List<ObjectError> errors = result.getAllErrors();
        // ViolationFieldError
        for (ObjectError error : errors) {
            log.error("未通过参数校验: {}", error.toString());
        }
        return HttpResult.fail(HttpStatus.BAD_REQUEST.value(), "请求错误", null);
    }

    // /**
    //  * 自定义参数错误异常处理器
    //  *
    //  * @param e 自定义参数
    //  * @return ResponseInfo
    //  */
    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    // @ExceptionHandler({ParamErrorException.class})
    // public ResponseResult paramExceptionHandler(ParamErrorException e) {
    //     log.error("", e);
    //     log.error("wglwglwgwgwg");
    //     // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
    //     if (StringUtils.hasText(e.getMessage())) {
    //         return new ResponseResult(ResultEnum.PARAMETER_ERROR.getCode(), e.getMessage());
    //     }
    //     return new ResponseResult(ResultEnum.PARAMETER_ERROR);
    // }

    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    // @ExceptionHandler({ConstraintViolationException.class})
    // public ResponseResult handleConstraintViolationException(ConstraintViolationException e) {
    //     log.error("", e);
    //     log.error("是异常");
    //     // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
    //     if (StringUtils.hasText(e.getMessage())) {
    //         return new ResponseResult(ResultEnum.PARAMETER_ERROR.getCode(), e.getMessage());
    //     }
    //     return new ResponseResult(ResultEnum.PARAMETER_ERROR);
    // }

    // @ExceptionHandler(value = BindException.class)
    // public Object bindException(BindException ex) {

    //     BindingResult bindingResult = ex.getBindingResult();

    //     // 获取所有的错误信息                                                                                        
    //     List<ObjectError> allErrors = bindingResult.getAllErrors();

    //     // 输出
    //     allErrors.forEach(e -> System.out.println(e.getDefaultMessage()));

    //     return "请求参数错误";
    // }

    // @ExceptionHandler(value = Exception.class)
    // // @ResponseBody
    // public void handle(Exception e) {
    //     System.out.println("gggg");
    //     // BaseResponse response = new BaseResponse();
    //     // response.setRsbCode(RsbCode.Rsb_SYS_ERR, e.getMessage());
    //     // return response;
    // }
}
