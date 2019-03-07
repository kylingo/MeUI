package com.me.ui.sample.thirdparty.aspect;


import com.android.component.library.util.LogUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author kylingo
 * @since 2019/03/07 16:24
 */
@Aspect
public class TryCatchAspect {

    @Around("execution(@com.me.ui.sample.thirdparty.aspect.TryCatch * *(..))")
    public Object excuteTryCatchMethod(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            LogUtils.e(TraceAspect.TAG, "excuteTryCatchMethod");
            e.printStackTrace();
        }

        return null;
    }
}
