package edu.ktu.ryselis.parameterConstraints;

/**
 * Created by ryselis on 16.5.18.
 */
public class IntegerEqualsParameterConstraint implements ParameterConstraint {
    private int equalsTo;

    public IntegerEqualsParameterConstraint(int equalsTo) {
        this.equalsTo = equalsTo;
    }

    @Override
    public double validateValue(Object value) {
        int v = (Integer) value;
        if (v == equalsTo){
            return 0;
        }
        double mismatch = Math.abs((double) v - equalsTo);
        double vAbs = Math.abs(v);
        double equalsToAbs = Math.abs(equalsTo);
        double divisor = vAbs > equalsToAbs ? vAbs : equalsToAbs;
        double result = mismatch / divisor;
        if (result > 1){
            result = 1;
        }
        return result;
    }

    @Override
    public ParameterConstraint getInverse() {
        return new IntegerNotEqualsParameterConstraint(equalsTo);
    }
}
