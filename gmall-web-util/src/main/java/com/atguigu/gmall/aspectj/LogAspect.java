package com.atguigu.gmall.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author dalaoban
 * @create 2020-04-05-21:40
 */
/*切日志注解*/
@Aspect
@Component
public class LogAspect {

    /*设置切入点*/
    @Pointcut("@annotation(com.atguigu.gmall.annotations.Log)")
    public void logPointCut(){
    }


    /**
     * 在方法之后前执行（不论是否抛出异常）
            * @param joinPoint
     */
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint){

    }

    /**
     * 在方法之后执行（不论是否抛出异常）
     * @param joinPoint
     */
    @After("logPointCut()")
    public void doAfter(JoinPoint joinPoint){

    }


    /**
     * 在方法正常执行后的通知
     * 返回通知是可以访问到方法的返回值的
     * @param joinPoint
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint){

    }

    /**
     * 在目标方法出现异常时会执行的代码
     * 可以访问出现的异常信息，可以指定出现指定异常时执行
     * 方法参数Exception改为其它异常可以指定出现指定异常时执行
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(pointcut = "logPointCut()",throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint,Exception ex){

    }

    /**
     * 环绕通知
     *    需要携带ProceedingJoinPoint类型的参数
     * 类似于动态代理的全过程：ProceedingJoinPoint类型参数可以决定是否执行目标方法
     * 环绕通知必须有返回值，返回值就是目标方法的返回值
     * @param joinPoint
     */
    @Around("logPointCut()")
    public Object  doAround(ProceedingJoinPoint joinPoint){
        Object result=null;
        String methodName=joinPoint.getSignature().getName();
        try {
            //前置通知
            System.out.println("---->The method "+methodName+" begins with" + Arrays.asList(joinPoint.getArgs()));
            //执行目标方法
            result=joinPoint.proceed();
            //返回通知
            System.out.println("---->"+result);
        } catch (Throwable e) {
            e.printStackTrace();
            //异常通知
            System.out.println("---->"+e);
        }

        //后置通知
        System.out.println("---->The method "+methodName+ "ends");
        return result;
    }
}
