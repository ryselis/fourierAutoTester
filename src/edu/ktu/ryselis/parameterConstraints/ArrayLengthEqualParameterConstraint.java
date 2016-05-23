package edu.ktu.ryselis.parameterConstraints;

/**
 * Created by ryselis on 16.5.22.
 */
public class ArrayLengthEqualParameterConstraint implements ParameterConstraint {
    @Override
    public double validateValue(Object value) {
        Object[] values = (Object[]) value;
        Object[] v1 = (Object[]) values[0];
        Object[] v2 = (Object[]) values[1];
        int v1Length = v1.length;
        int v2Length = v2.length;
        if (v1Length == v2Length) {
            return 0;
        }
        if (v1Length == 0 || v2Length == 0){
            v1Length++;
            v2Length++;
        }
        double diff = Math.abs(v1Length - v2Length);
        int sum = v1Length + v2Length;
        return diff / sum;
    }

    @Override
    public ParameterConstraint getInverse() {
        return new ArrayLengthNotEqualParameterConstraint();
    }

    @Override
    public boolean acceptsSingleValue() {
        return false;
    }
}
