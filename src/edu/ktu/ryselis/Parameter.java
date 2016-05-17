package edu.ktu.ryselis;

import java.lang.reflect.Type;

/**
 * Created by ryselis on 16.5.14.
 */
public class Parameter<T> {
    private Type type;
    private T value;
    private Object[] appliedConstructorParameters;

    public Parameter(T value, Object[] appliedConstructorParameters) {
        this.type = value.getClass();
        this.value = value;
        this.appliedConstructorParameters = appliedConstructorParameters;
    }

    public Type getType() {
        return type;
    }

    public T getValue() {
        return value;
    }

    public Object[] getAppliedConstructorParameters() {
        return appliedConstructorParameters;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
