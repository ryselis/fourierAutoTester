package edu.ktu.ryselis;

import java.util.Collection;

/**
 * Created by ryselis on 16.5.14.
 */
class TestCase {
    private Collection<Parameter> parameters;
    private Method method;

    public TestCase(Method method, Collection<Parameter> parameters){
        this.parameters = parameters;
        this.method = method;
    }
}
