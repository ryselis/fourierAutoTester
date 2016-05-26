package edu.ktu.ryselis.parameterConstraints;

/**
 * Created by ryselis on 16.5.25.
 */
public class IntegerLessOrEqualTo implements ParameterConstraint {
    private int lessOrEqualTo;

    public IntegerLessOrEqualTo(int lessOrEqualTo) {
        this.lessOrEqualTo = lessOrEqualTo;
    }

    @Override
    public double validateValue(Object value) {
        int v = (Integer) value;
        if (v <= lessOrEqualTo){
            return 0;
        }
        double mismatch = Math.abs((double) v - lessOrEqualTo);
        double vAbs = Math.abs(v);
        double equalsToAbs = Math.abs(lessOrEqualTo);
        double divisor = vAbs > equalsToAbs ? vAbs : equalsToAbs;
        double result = mismatch / divisor;
        if (result > 1){
            result = 1;
        }
        return result;
    }

    @Override
    public ParameterConstraint getInverse() {
        return new IntegerGreaterThanParameterConstraint(lessOrEqualTo);
    }

    @Override
    public boolean acceptsSingleValue() {
        return true;
    }

    @Override
    public boolean compliesWithPowerOf2Constraint() {
        return lessOrEqualTo >= 2;
    }
}
