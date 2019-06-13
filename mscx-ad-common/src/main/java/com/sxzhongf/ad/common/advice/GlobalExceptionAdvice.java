package com.sxzhongf.ad.common.advice;

import com.sxzhongf.ad.common.exception.AdException;
import com.sxzhongf.ad.common.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * GlobalExceptionAdvice for 全局统一异常拦截
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang</a>
 * @see RestControllerAdvice
 * @see ExceptionHandler
 * @since 2019/6/13
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 对 {@link AdException} 进行统一处理
     * {@link ExceptionHandler}  对指定的异常进行拦截
     * 可优化：
     * 定义多种类异常，实现对应的异常处理，
     * 例如：
     * <ul>
     * <li>
     * 推广单元操作异常，抛出 AdUnitException
     * </li>
     * <li>
     * Binlog 解析异常，抛出 BinlogException
     * </li>
     * </ul>
     * 拦截Spring Exception 使用 {@link ExceptionHandler}注解
     */
    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest request, AdException ex) {
        CommonResponse<String> response = new CommonResponse<>(-1, "business error");
        response.setData(ex.getMessage());
        return response;
    }
}
