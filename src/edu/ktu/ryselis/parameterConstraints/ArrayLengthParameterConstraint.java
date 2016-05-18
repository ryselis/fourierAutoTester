package edu.ktu.ryselis.parameterConstraints;

import java.util.Collection;

/**
 * Created by ryselis on 16.5.18.
 */
public class ArrayLengthParameterConstraint implements ParameterConstraint {
    private ParameterConstraint singleValueConstraint;

    public ArrayLengthParameterConstraint(ParameterConstraint singleValueConstraint) {
        this.singleValueConstraint = singleValueConstraint;
    }

    @Override
    public double validateValue(Object value) {
        Collection collection = (Collection) value;
        return singleValueConstraint.validateValue(collection.size());
    }

    @Override
    public ParameterConstraint getInverse() {
        return this;
    }
}
