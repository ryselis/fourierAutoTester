package edu.ktu.petkus;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Tautvydas on 5/24/2016.
 */
public class MethodInvoker implements Runnable {

    @Override
    public void run() {
        try {
            java.lang.reflect.Method method = MethodInvokerStaticParametersHolder.method;
            // cia kur setinasi sitas, reikia kazka kita setint
            Object mutantObject = MethodInvokerStaticParametersHolder.mutantObject;
            Object[] paramsObjects = MethodInvokerStaticParametersHolder.paramsObjects;
            // cia kazkas negerai yra
            // mutant object yra ne objektas, o pati klase
//            Object[] betterParamsObjects = new Object[paramsObjects.length];
//            for (int i = 0; i < paramsObjects.length; i++){
//                betterParamsObjects[i] = method.getParameterTypes()[i].cast(paramsObjects[i]);
//            }
//            try {
//                Object[] resultObjects = (Object[]) method.invoke(mutantObject, paramsObjects);
//                MethodInvokerStaticParametersHolder.resultObjects = resultObjects;
//            } catch (IllegalAccessException e) {
//                MethodInvokerStaticParametersHolder.exceptionOccurred = true;
//            } catch (InvocationTargetException e) {
//                MethodInvokerStaticParametersHolder.exceptionOccurred = true;
//            }
            Thread thread = new AsyncMethodInvoker(method, mutantObject, paramsObjects);
            thread.start();
            try {
                thread.join(10000);
            } catch (InterruptedException e) {
                MethodInvokerStaticParametersHolder.exceptionOccurred = true;
            }
            if (thread.isAlive()) {
                thread.stop();
                MethodInvokerStaticParametersHolder.exceptionOccurred = true;
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
