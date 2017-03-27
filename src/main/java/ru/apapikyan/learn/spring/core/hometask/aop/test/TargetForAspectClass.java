package ru.apapikyan.learn.spring.core.hometask.aop.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Artur_Papikyan on 27.04.2016.
 */
public class TargetForAspectClass implements TargetForAspectInterface {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("test/test-aspects.xml");
        TargetForAspectInterface targetForAspect = (TargetForAspectInterface) context.getBean("targetForAspect");

        //targetForAspect.MethodWOparametersAndWORetVal(); //1

        //targetForAspect.MethodWOparametersAndWithRetVal(); //2

        targetForAspect.methodWithparametersAndRetVal("param1", "param2"); //4

        targetForAspect.methodInnerWOParams(); //5

        System.out.println("end");
    }

    @Override
    public void methodWOparametersAndWORetVal() {
        System.out.println("--==MethodWOparametersAndWORetVal==--");
    }

    @Override
    public String methodWOparametersAndWithRetVal() {
        String retVal = "Return value";
        System.out.println("MethodWOparametersAndWithRetVal: " + retVal);
        return retVal;
    }

    @Override
    public void methodWithparametersAndWORetVal(String param1, String param2) {
        System.out.println(String.format("MethodWithparametersAndWORetVal: [p1=%s];[p2=%s]", param1, param2));
    }

    @Override
    public String methodWithparametersAndRetVal(String param1, String param2) {
        String retVal = "bla-bla:" + param1 + " - " + param2;

        System.out.println(String.format("MethodWithparametersAndWORetVal: [p1=%s];[p2=%s];[retVal=%s]", param1, param2, retVal));

        //methodInnerWOParams();

        //((TargetForAspectInterface)this).methodInnerWOParams();

        return retVal;
    }

    @Override
    public void methodInnerWOParams() {
        System.out.println("MethodInnerWOParams");
    }
}
