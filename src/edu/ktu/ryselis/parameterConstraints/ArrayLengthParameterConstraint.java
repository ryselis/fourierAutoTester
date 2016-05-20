package edu.ktu.ryselis.parameterConstraints;

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
        Object[] collection = (Object[]) value;
        return singleValueConstraint.validateValue(collection.length);
    }

    @Override
    public ParameterConstraint getInverse() {
        return new ArrayLengthParameterConstraint(singleValueConstraint.getInverse());
    }
}
