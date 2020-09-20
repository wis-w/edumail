package com.edu.gulimall.product.exeception;

import com.edu.common.exception.BizCodeEnume;
import com.edu.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 集中的异常处理类
 */
//@RestController
//@ControllerAdvice(basePackages = "com.edu.gulimall.product.controller")
@Slf4j
@RestControllerAdvice(basePackages = "com.edu.gulimall.product.controller") // 相当于@RestController + @ControllerAdvice
public class GulimallExeceptionControllerAdvice {

    /**
     * JSR303数据校验引起的异常，同一处理机制
     * @param e 数据校验异常
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handlerValidException(MethodArgumentNotValidException e){
        log.info("数据校验处理异常：{},异常类型{}",e.getMessage(),e.getClass());
        Map<String, String> map = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach((item)->{
            map.put(item.getField(), item.getDefaultMessage());
        });
        return R.error(BizCodeEnume.VAILD_EXCPTION.getCode(),BizCodeEnume.VAILD_EXCPTION.getMessage()).put("data", map);
    }

    /**
     * 未知的全局异常捕获机制
     * @param t
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    public R handlerException(Throwable t) {
        log.info("未知的异常捕获机制:{},异常类型:{}", t.getMessage(), t.getClass());
        return R.error(BizCodeEnume.UNKNOW_EXCPTION.getCode(), BizCodeEnume.UNKNOW_EXCPTION.getMessage());
    }

}
