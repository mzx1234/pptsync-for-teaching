package com.mzx.pptparseserver.aop;

import com.mzx.pptcommon.utility.StringUtils;
import com.mzx.pptparseserver.annotation.Cacheable;
import com.mzx.pptparseserver.utility.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zison on 2016/4/24.
 */
@Aspect
@Component
public class CachedAspect extends AbstractAspect {

    @Autowired
    private RedisUtil redisUtil;




    /**
     *
     */
    @Pointcut("@annotation(com.mzx.pptparseserver.annotation.Cacheable)")

    protected void cacheablePointcut() {}

    /**
     *
     * @param pjp
     * @return
     */
//    @Around("execution(* com.mzx.pptparseserver.service.ParseToImgService.*(..))")
    @Around("cacheablePointcut()")
    public Object getCacheable(ProceedingJoinPoint pjp) {

        logger.info("getCaheable method is running");


        Object result = null;
        String key = getKey(pjp);
        if(StringUtils.isEmpty(key)) {
            try {
                result = pjp.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return result;
        }

        result = redisUtil.hGetLen(key);
        if(result == null || (Long) result == 0) {
            try {
                result = pjp.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return result;
        }

        return result;

    }


    private String getKey(ProceedingJoinPoint pjp) {

        logger.info("getKey method is running");

        StringBuilder sb = new StringBuilder();
//        Cacheable cacheable = getMethod(pjp).getAnnotation(com.mzx.pptparseserver.annotation.Cacheable.class);
//        sb.append(cacheable.key());
        Object[] args = pjp.getArgs();

        if(args != null) {
            for(Object arg : args) {
                sb.append(arg.toString());
                break;
            }
        }
        return sb.toString();
    }
}
