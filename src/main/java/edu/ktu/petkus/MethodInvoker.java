package edu.ktu.petkus;

/**
 * Created by Tautvydas on 5/24/2016.
 */
public class MethodInvoker implements Runnable {

    @Override
    public void run() {
        try {
            MethodInvokerStaticParametersHolder.resultObjects =
                    (Object[]) MethodInvokerStaticParametersHolder.method.invoke(
                            MethodInvokerStaticParametersHolder.mutantObject,
                            MethodInvokerStaticParametersHolder.paramsObjects
                    );
        } catch (Exception ex) {
            MethodInvokerStaticParametersHolder.exceptionOccurred = true;
        }
    }
}
