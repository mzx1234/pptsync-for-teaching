package com.mzx.pptparseserver.aop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * AspectJ框架切面抽象类，封装了一些切面通用的方法，如获取方法对象、方法名称等。
 *
 * @author zison
 */
public class AbstractAspect {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractAspect.class);
    protected Gson gson = new GsonBuilder().serializeNulls().create();

    /**
     *
     * @param joinPoint
     * @return
     */
    protected String getLogHeader(JoinPoint joinPoint) {
        return getClassName(joinPoint) + "." + getMethodName(joinPoint);
    }

    /**
     *
     * @param joinPoint
     * @return
     */
    protected String getMethodName(JoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
    }

    /**
     *
     * @param joinPoint
     * @return
     */
    protected String getClassName(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass().getName();
    }

    /**
     * 根据连接点对象，获取方法。
     *
     * @param joinPoint 连接点对象。
     * @return 获取方法对象。
     */
    protected Method getMethod(JoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod();
    }
}
