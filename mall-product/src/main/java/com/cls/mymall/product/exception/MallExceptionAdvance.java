package com.cls.mymall.product.exception;

import com.cls.mymall.common.exception.BizCode;
import com.cls.mymall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice(basePackages = "com.cls.mymall.product.controller")
public class MallExceptionAdvance {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{}，异常处理：{}", e.getMessage(), e.getClass());
        BindingResult result = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        result.getFieldErrors().forEach((error) -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return R.error(BizCode.VALID_EXCEPTION.getCode(), BizCode.VALID_EXCEPTION.getMsg()).put("data", errorMap);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {
        System.out.println("throwable.getMessage() = " + throwable.getMessage());
        return R.error(BizCode.UNKNOWN_EXCEPTION.getCode(), BizCode.UNKNOWN_EXCEPTION.getMsg());
    }

}
