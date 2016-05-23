package edu.ktu.ryselis;

/**
 * Created by ryselis on 16.5.14.
 */
public class Method {
    private java.lang.reflect.Method method;
    private String pathToClassSource;

    public Method(java.lang.reflect.Method method, String pathToClassSource) {
        this.method = method;
        this.pathToClassSource = pathToClassSource;
    }

    public java.lang.reflect.Method getMethod() {
        return method;
    }

    public String getPathToClassSource() {
        return pathToClassSource;
    }
}
