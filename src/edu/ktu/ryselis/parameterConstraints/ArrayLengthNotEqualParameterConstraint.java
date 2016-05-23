package edu.ktu.ryselis.parameterConstraints;

/**
 * Created by ryselis on 16.5.22.
 */
public class ArrayLengthNotEqualParameterConstraint implements ParameterConstraint {
    @Override
    public double validateValue(Object value) {
        Object[] values = (Object[]) value;
        Object[] v1 = (Object[]) values[0];
        Object[] v2 = (Object[]) values[1];
        if (v1.length == v2.length){
            return 1;
        }
        return 0;
    }

    @Override
    public ParameterConstraint getInverse() {
        return new ArrayLengthEqualParameterConstraint();
    }

    @Override
    public boolean acceptsSingleValue() {
        return false;
    }
}
