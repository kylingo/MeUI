package com.me.ui.sample.thirdparty.aspect;

import com.android.component.library.util.LogUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author kylingo
 * @since 2019/03/07 11:01
 */
@Aspect
public class TraceAspect {
    public static final String TAG = "TraceAspect";
    private static final String POINT_METHOD = "execution(* com.me.ui.sample.thirdparty.aspect.AspectFragment.testAspect(..))";
    private static final String POINT_CALLMETHOD = "call(* com.me.ui.sample.thirdparty.aspect.AspectFragment..testAspect(..))";

    @Pointcut(POINT_METHOD)
    public void methodAnnotated() {
    }

    @Pointcut(POINT_CALLMETHOD)
    public void methodCallAnnotated() {
    }

    @Around("methodAnnotated()")
    public Object aronudWeaverPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Object object = joinPoint.proceed();
        String result = "aroundWeaverPoint";
        LogUtils.i(TAG, "testAspect aroundWeaverPoint");
//        return result;//替换原方法的返回值
        return object;
    }

    @Before("methodCallAnnotated()")
    public void beforeCall(JoinPoint joinPoint) {
        LogUtils.i(TAG, "testAspect beforeCall");
    }

    @After("methodCallAnnotated()")
    public void afterCall(JoinPoint joinPoint) {
        LogUtils.i(TAG, "testAspect afterCall");
    }

    @Before("call(* com.me.ui.sample.thirdparty.aspect.AspectFragment..testAspect1(..))")
    public void executeAspectBefore(JoinPoint joinPoint) {
        LogUtils.i(TAG, "testAspect1 beforeCall");
    }

    @After("call(* com.me.ui.sample.thirdparty.aspect.AspectFragment..testAspect1(..))")
    public void executeAspectAfter(JoinPoint joinPoint) {
        LogUtils.i(TAG, "testAspect1 afterCall");
    }
}