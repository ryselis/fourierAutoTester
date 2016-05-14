package edu.ktu.ryselis;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by ryselis on 16.5.14.
 */
public class NeighbourChooser {
    private Parameter parameter;

    public NeighbourChooser(Parameter parameter) {
        this.parameter = parameter;
    }

    private static int chooseNeighbour(int original){
        int upperBound = Integer.MAX_VALUE / 1000000;
        int lowerBound = Integer.MIN_VALUE / 1000000;
        int variance = (int) (Math.random() * (upperBound - lowerBound) + lowerBound);
        long result = original + variance;
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE){
            result = original;
        }
        return (int) result;
    }

    private static double chooseNeighbour(double original){
        double upperBound = Double.MAX_VALUE / 1000000;
        double lowerBound = Double.MIN_VALUE / 1000000;
        double variance = Math.random() * (upperBound - lowerBound) + lowerBound;
        double result = original + variance;
        if (result > Double.MAX_VALUE || result < Double.MIN_VALUE){
            result = original;
        }
        return result;
    }

    public Parameter constructNewParameter() {
        Class cl = parameter.getValue().getClass();
        Constructor[] constructors = cl.getDeclaredConstructors();
        Object object;
        Object[] newlyAppliedConstructorArgs = new Object[0];
        if (constructors.length == 0){
            try {
                Constructor constructor = cl.getConstructor();
                object = constructor.newInstance();
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                object = null;
            }
        } else {
            Constructor constructor = constructors[0];
            Class[] parameterTypes = constructor.getParameterTypes();
            Object[] appliedConstructorArgs = parameter.getAppliedConstructorParameters();
            newlyAppliedConstructorArgs = new Object[appliedConstructorArgs.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                Class parameterType = parameterTypes[i];
                if (parameterType == Integer.TYPE) {
                    int previouslyAppliedArg = (int) appliedConstructorArgs[i];
                    int newValue = chooseNeighbour(previouslyAppliedArg);
                    newlyAppliedConstructorArgs[i] = newValue;
                }
                if (parameterType == Double.TYPE){
                    double previouslyAppliedArg = (double) appliedConstructorArgs[i];
                    double newValue = chooseNeighbour(previouslyAppliedArg);
                    newlyAppliedConstructorArgs[i] = newValue;
                }
            }
            try {
                object = constructor.newInstance(newlyAppliedConstructorArgs);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                object = null;
            }

        }
        return new Parameter(cl.cast(object), newlyAppliedConstructorArgs);
    }
}
