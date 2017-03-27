package ru.apapikyan.learn.spring.core.hometask.service.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.apapikyan.learn.spring.core.hometask.domain.User;

import java.util.*;

/**
 * Created by Artur_Papikyan on 20.04.2016.
 */
@Aspect
public class DiscountAspect {

    private Map<Class<?>, Integer> discountsCounter;
    private Map<List<String>, Integer> discountPerUser;

    public DiscountAspect() {
        discountsCounter = new HashMap<>();
        discountPerUser = new HashMap<>();
    }

    @Pointcut("execution(* *.calculateDiscount(..))")
    private void callCalculateDiscountMethod() {
    }

    @Around("callCalculateDiscountMethod()")
    public byte adviceAroundCalculateDiscount(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("adviceAroundCalculateDiscount:" + proceedingJoinPoint.getTarget().getClass().getSimpleName());

        Object midVal = proceedingJoinPoint.proceed();
        Byte retVal = 0;
        if (midVal != null) {
            retVal = Byte.valueOf(midVal.toString());
        }

        if (retVal > 0) {
            Class<?> clazz = proceedingJoinPoint.getTarget().getClass();
            if (!discountsCounter.containsKey(clazz)) {
                discountsCounter.put(clazz, 0);
            }

            discountsCounter.put(clazz, discountsCounter.get(clazz) + 1);

            User user = (User) proceedingJoinPoint.getArgs()[0];
            List<String> discountPerUserKey = Collections.unmodifiableList(Arrays.asList(user.getEmail(), clazz.toString()));
            if (!discountPerUser.containsKey(discountPerUserKey)) {
                discountPerUser.put(discountPerUserKey, 0);
            }

            discountPerUser.put(discountPerUserKey, discountPerUser.get(discountPerUserKey) + 1);
        }

        return retVal;
    }

    @Override
    public String toString() {
        return "DiscountAspect{" +
                "discountsCounter = " + discountsCounter.values().size() +
                "; discountPerUser = " + discountPerUser.values().size() +
                '}';
    }
}