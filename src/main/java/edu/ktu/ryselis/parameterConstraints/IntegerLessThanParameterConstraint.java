package edu.ktu.ryselis.parameterConstraints;

/**
 * Created by ryselis on 16.5.25.
 */
public class IntegerLessThanParameterConstraint implements ParameterConstraint {
    private int greaterThan;

    public IntegerLessThanParameterConstraint(int greaterThan) {
        this.greaterThan = greaterThan;
    }

    @Override
    public double validateValue(Object value) {
        int v = (Integer) value;
        if (v < greaterThan){
            return 0;
        }
        double mismatch = Math.abs((double) v - greaterThan);
        double vAbs = Math.abs(v);
        double equalsToAbs = Math.abs(greaterThan);
        double divisor = vAbs > equalsToAbs ? vAbs : equalsToAbs;
        double result = mismatch / divisor;
        if (result > 1){
            result = 1;
        }
        return result;
    }

    @Override
    public ParameterConstraint getInverse() {
        return new IntegerGreaterThanOrEqualsParameterContraint(greaterThan);
    }

    @Override
    public boolean acceptsSingleValue() {
        return true;
    }
}
