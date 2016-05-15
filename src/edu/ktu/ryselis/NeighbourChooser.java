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

    /**
     * Method scans how the class given in parameter is constructed and creates a new mutated parameter from a given
     * parameter. If only the default constructor is available, calls it, else finds first available constructor,
     * generates parameters for it and then calls it. The initial parameters have to be in initial parameters as it is
     * hard to tell what the constructor is doing inside. Mutations currently work only for integer and double
     * constructor parameters and on arrays of objects that would accept those types as constructor parameters.
     * @return a mutated parameter
     */
    public Parameter constructNewParameter() {
        if (parameter.getValue() instanceof Object[]){
            Object[] values = (Object[]) parameter.getValue();
            Parameter[] newParameters = new Parameter[values.length];
            for (int i = 0; i < values.length; i++) {
                Object value = values[i];
                Object[] appliedConstructorArgsForValue = (Object[]) parameter.getAppliedConstructorParameters()[i];
                Parameter parameterForSingleValue = new Parameter(value, appliedConstructorArgsForValue);
                NeighbourChooser chooserForSingleValue = new NeighbourChooser(parameterForSingleValue);
                Parameter mutatedParameterForSingleValue = chooserForSingleValue.constructNewParameter();
                newParameters[i] = mutatedParameterForSingleValue;
            }
            Object[] newValues = new Object[values.length];
            Object[][] newConstructorArgs = new Object[values.length][];
            for (int i = 0; i < values.length; i++){
                newValues[i] = newParameters[i].getValue();
                newConstructorArgs[i] = newParameters[i].getAppliedConstructorParameters();
            }
            return new Parameter(newValues, newConstructorArgs);
        }
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
