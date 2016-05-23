package edu.ktu.ryselis;

/**
 * Created by ryselis on 16.5.16.
 */
public class ParameterDefinition {
    private Class type;

    public ParameterDefinition(Class type) {
        this.type = type;
    }

    public Class getType() {
        return type;
    }
}
