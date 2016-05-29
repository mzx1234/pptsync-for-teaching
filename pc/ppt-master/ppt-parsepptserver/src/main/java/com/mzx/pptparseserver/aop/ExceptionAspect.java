package com.mzx.pptparseserver.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * Created by zison on 2016/1/11.
 */
//@Component
//@Aspect
//@Order(3)
public class ExceptionAspect extends AbstractAspect {



    @AfterThrowing(throwing="ex",pointcut="execution(* com.mzx.pptparseserver..*.*(..))")
    public void printException(JoinPoint jp, Throwable ex) {
        logger.error("is error");
    }



//    /**
//     * 打印方法异常日志。
//     *
//     * @param jp 切入点
//     * @param e  异常
//     */
//    protected void printLogMethodException(JoinPoint jp, Throwable e) {
//        logger.error("-----------------printLogMethodException start--------------------------");
//        logger.error(getLogHeader(jp) + " exception## arguments: " + gson.toJson(jp.getArgs()));
//        logger.error(getLogHeader(jp) + " exception## " + e.getMessage(), e);
//        logger.error("-----------------printLogMethodException end---------------------------");
//    }

}
