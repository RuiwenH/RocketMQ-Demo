package com.reven.core.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;


/**
 * @ClassName: GlobalExceptionHandler
 * @author reven
 * @date 2018年8月30日
 */
@RestControllerAdvice
public class GlobalValidationExceptionHandler {
    public static final String ERROR_VIEW = "common/error";

    @ExceptionHandler(BindException.class)
    public Object handleConstraintViolationException(HttpServletRequest request, HttpServletResponse resp,
            BindException ex) throws IOException {

        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        StringBuffer errorMsg = new StringBuffer();
        errors.stream().forEach(x -> errorMsg.append(x.getDefaultMessage()).append(";"));
        String msg = errorMsg.toString();

        if (isAjax(request)) {
            return ResResult.fail(msg);
        } else {
            ModelAndView mav = new ModelAndView();
            mav.addObject("exception", ex);
            mav.addObject("url", request.getRequestURL());
            mav.setViewName(ERROR_VIEW);
            return mav;
        }
    }
    
    /**
     * * 判断是否是ajax请求
     * 
     * @param httpRequest
     * @return
     */
    public static boolean isAjax(HttpServletRequest httpRequest) {
        String xRequestedWith = httpRequest.getHeader("X-Requested-With");
        return (xRequestedWith != null && "XMLHttpRequest".equals(xRequestedWith));
    }
}
