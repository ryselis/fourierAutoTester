package edu.ktu.petkus;

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
            Object[] resultObjects = (Object[]) method.invoke(mutantObject, paramsObjects);
            MethodInvokerStaticParametersHolder.resultObjects = resultObjects;
        } catch (Exception ex) {
            MethodInvokerStaticParametersHolder.exceptionOccurred = true;
        }
    }
}
