package com.edu.common.vaild;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义校验注解，显示状态校验
 */
@Documented
@Constraint(validatedBy = { ListValueConsteaintValidator.class })// 使用什么进行校验
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })// 可以标注在那些地方
@Retention(RUNTIME)// 运行时使用
public @interface ListValue {

    String message() default "{com.edu.common.vaild.ListValue.message}";// 从配置文件中取出该文本信息

    Class<?>[] groups() default { };// 分组校验支持

    Class<? extends Payload>[] payload() default { };

    int [] vals() default {};
}
