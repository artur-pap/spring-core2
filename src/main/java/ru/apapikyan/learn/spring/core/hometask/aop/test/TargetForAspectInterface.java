package ru.apapikyan.learn.spring.core.hometask.aop.test;

/**
 * Created by Artur_Papikyan on 27.04.2016.
 */
public interface TargetForAspectInterface {
    void methodWOparametersAndWORetVal();

    public String methodWOparametersAndWithRetVal();

    public void methodWithparametersAndWORetVal(String param1, String param2);

    public String methodWithparametersAndRetVal(String param1, String param2);

    public void methodInnerWOParams();
}
