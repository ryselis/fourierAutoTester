package edu.ktu.ryselis.parameterConstraints;

/**
 * Created by ryselis on 16.5.18.
 */
public interface ParameterConstraint {
    double validateValue(Object value);
    ParameterConstraint getInverse();
}
