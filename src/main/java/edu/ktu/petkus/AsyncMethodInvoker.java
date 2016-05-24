package edu.ktu.petkus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ryselis on 16.5.25.
 */
public class AsyncMethodInvoker extends Thread {
    private Method method;
    private Object mutantObject;
    private Object[] paramsObjects;

    public AsyncMethodInvoker(Method method, Object mutantObject, Object[] paramsObjects) {
        this.method = method;
        this.mutantObject = mutantObject;
        this.paramsObjects = paramsObjects;
    }

    @Override
    public void run() {
        try {
            Object[] resultObjects = (Object[]) method.invoke(mutantObject, paramsObjects);
            MethodInvokerStaticParametersHolder.resultObjects = resultObjects;
        } catch (IllegalAccessException e) {
            MethodInvokerStaticParametersHolder.exceptionOccurred = true;
        } catch (InvocationTargetException e) {
            MethodInvokerStaticParametersHolder.exceptionOccurred = true;
        }
    }
}
