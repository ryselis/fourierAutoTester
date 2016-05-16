package edu.ktu.ryselis;

import java.lang.reflect.*;
import java.lang.reflect.Method;
import java.math.BigInteger;

/**
 * Created by ryselis on 16.5.14.
 */
public class ObjectGenerator {

    /**
     * Method scans how the class given in parameter is constructed and creates a new mutated parameter from a given
     * parameter. If only the default constructor is available, calls it, else finds first available constructor,
     * generates parameters for it and then calls it. The initial parameters have to be in initial parameters as it is
     * hard to tell what the constructor is doing inside. Mutations currently work only for integer and double
     * constructor parameters and on arrays of objects that would accept those types as constructor parameters.
     * @param parameter an existing parameter we want to mutate
     * @return a mutated parameter
     */
    public Parameter constructNewParameter(Parameter parameter) {
        ValueGenerator valueGenerator = new NeighbourValueGenerator();
        if (parameter.getValue() instanceof Object[]){
            Object[] values = (Object[]) parameter.getValue();
            Parameter[] newParameters = new Parameter[values.length];
            for (int i = 0; i < values.length; i++) {
                Object value = values[i];
                Object[] appliedConstructorArgsForValue = (Object[]) parameter.getAppliedConstructorParameters()[i];
                Parameter parameterForSingleValue = new Parameter(value, appliedConstructorArgsForValue);
                ObjectGenerator chooserForSingleValue = new ObjectGenerator();
                Parameter mutatedParameterForSingleValue = chooserForSingleValue.constructNewParameter(parameterForSingleValue);
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
                    int newValue = valueGenerator.generateValue(previouslyAppliedArg);
                    newlyAppliedConstructorArgs[i] = newValue;
                }
                if (parameterType == Double.TYPE){
                    double previouslyAppliedArg = (double) appliedConstructorArgs[i];
                    double newValue = valueGenerator.generateValue(previouslyAppliedArg);
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

    /**
     * Works similarly to constructNewParameter, but creates a new parameter with random constructor parameters.
     * @param parameterDefinition a parameter definition we use to create parameter
     * @return a randomly generated parameter
     */
    public Parameter constructRandomParameter(ParameterDefinition parameterDefinition){
        Class t = parameterDefinition.getType();
        if (t.isArray()){
            Class innerClass = t.getComponentType();
            ParameterDefinition innerClassParameterDefinition = new ParameterDefinition(innerClass);
            int arraySize = power(2, (int) (Math.random() * 11));
            Object[] newInstance = (Object[]) Array.newInstance(innerClass, arraySize);
            Object[][] newConstructorArgs = new Object[arraySize][];
            for (int i = 0; i < arraySize; i++){
                Parameter randomParameter = constructRandomParameter(innerClassParameterDefinition);
                newInstance[i] = randomParameter.getValue();
                newConstructorArgs[i] = randomParameter.getAppliedConstructorParameters();
            }
            return new Parameter(newInstance, newConstructorArgs);
        }
        Constructor[] constructors = t.getDeclaredConstructors();
        Object object;
        Object[] newlyAppliedConstructorArgs = new Object[0];
        if (constructors.length == 0){
            try {
                Constructor constructor = t.getConstructor();
                object = constructor.newInstance();
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                object = null;
            }
        } else {
            Constructor constructor = constructors[0];
            Class[] parameterTypes = constructor.getParameterTypes();
            newlyAppliedConstructorArgs = new Object[parameterTypes.length];
            ValueGenerator valueGenerator = new RandomValueGenerator();
            for (int i = 0; i < parameterTypes.length; i++) {
                Class parameterType = parameterTypes[i];

                if (parameterType == Integer.TYPE) {
                    int newValue = valueGenerator.generateValue(0);
                    newlyAppliedConstructorArgs[i] = newValue;
                }
                if (parameterType == Double.TYPE){
                    double newValue = valueGenerator.generateValue(0.0);
                    newlyAppliedConstructorArgs[i] = newValue;
                }
            }
            try {
                object = constructor.newInstance(newlyAppliedConstructorArgs);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                object = null;
            }
        }
        return new Parameter(object, newlyAppliedConstructorArgs);
    }

    private int power(int base, int power){
        if (power == 0){
            return 1;
        }
        if (power == 1){
            return base;
        }
        if (power % 2 == 0){
            return power(base * base, power / 2);
        } else {
            return base * power(base * base, power / 2);
        }
    }
}
