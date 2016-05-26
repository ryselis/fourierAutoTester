package edu.ktu.ryselis.parameterConstraints;

/**
 * Created by ryselis on 16.5.26.
 */
public class PowerOf2ParameterConstraint implements ParameterConstraint {
    @Override
    public double validateValue(Object value) {
        int val = (Integer) value;
        double power = 1;
        double prevPower = 1;
        while (power < val){
            prevPower = power;
            power *= 2;
        }
        double diff1 = 1 - (prevPower / val);
        double diff2 = 1 - (val / power);
        return diff1 < diff2? diff1: diff2;
    }

    @Override
    public ParameterConstraint getInverse() {
        return null;
    }

    @Override
    public boolean acceptsSingleValue() {
        return true;
    }

    @Override
    public boolean compliesWithPowerOf2Constraint() {
        return true;
    }
}
