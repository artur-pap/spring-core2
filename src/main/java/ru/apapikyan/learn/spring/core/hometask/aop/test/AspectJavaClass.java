package ru.apapikyan.learn.spring.core.hometask.aop.test;

/**
 * Created by Artur_Papikyan on 27.04.2016.
 */

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class AspectJavaClass {

    @Pointcut("execution(* ru.apapikyan.learn.spring.core.hometask.aop.test.TargetForAspectInterface.methodWithparametersAndRetVal(..)) && args(param1,..)")
    private void callMethodWithparametersAndRetVal(String param1) {}

    @Before("callMethodWithparametersAndRetVal(param1)")
    public void beforeAdvice4(String param1) {
        System.out.println("beforeAdvice4");
        //System.out.println(joinPoint.getSignature().getName() + " beforeAdvice4");
    }

    @Pointcut("execution(* ru.apapikyan.learn.spring.core.hometask.aop.test.TargetForAspectInterface.methodInnerWOParams(..))")
    private void callMethodInnerWOParams() {}

    @After("callMethodInnerWOParams()")
    public void beforeAdvice5() {
        System.out.println("afterAdvice5");
    }

    /*********************************************************************************************************/
    @Pointcut("execution(* *.methodWOparametersAndWithRetVal(..))")
    public void callMethodWOparametersAndWithRetVal() {
    }

    @Before("callMethodWOparametersAndWithRetVal()")
    public void beforeAdvice2(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() + " beforeAdvice2");
    }

    @After("callMethodWOparametersAndWORetVal()")
    public void afterAdvice2(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() + " afterAdvice2");
    }

    @AfterReturning(
            pointcut = "execution(* *.methodWOparametersAndWithRetVal(..)))",
            returning = "returnValue"
    )
    public void afterReturning2(JoinPoint joinPoint, Object returnValue) {
        System.out.println(joinPoint.getSignature().getName() + " afterReturning2.start");
        System.out.println("Return value in advice afterReturning2: " + returnValue);
        System.out.println(joinPoint.getSignature().getName() + " afterReturning2.end");
    }

    /*******************************************************************************************************************/
    @Pointcut("execution(* TargetForAspectClass.methodWOparametersAndWORetVal(..))")
    public void callMethodWOparametersAndWORetVal() {
    }

    @Before("callMethodWOparametersAndWORetVal()")
    public void beforeAdvice1(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() + " beforeAdvice1");
    }

    @After("callMethodWOparametersAndWORetVal()")
    public void afterAdvice1(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() + " afterAdvice1");
    }

    @AfterReturning(pointcut = "callMethodWOparametersAndWORetVal()")
    public void afterReturning1(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() + " afterReturning1");
    }

    @Around("callMethodWOparametersAndWORetVal()")
    public void around1(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("around1. Before calling " + proceedingJoinPoint.getSignature().getName());
        proceedingJoinPoint.proceed();
        System.out.println("around1. After calling " + proceedingJoinPoint.getSignature().getName());
    }
}
