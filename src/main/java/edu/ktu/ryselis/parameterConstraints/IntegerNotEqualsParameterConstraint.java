package edu.ktu.ryselis.parameterConstraints;

/**
 * Created by ryselis on 16.5.18.
 */
public class IntegerNotEqualsParameterConstraint implements ParameterConstraint {
    private int notEqualTo;

    public IntegerNotEqualsParameterConstraint(int notEqualTo) {
        this.notEqualTo = notEqualTo;
    }

    @Override
    public double validateValue(Object value) {
        int val = (Integer) value;
        return val == notEqualTo ? 1 : 0;
    }

    @Override
    public ParameterConstraint getInverse() {
        return new IntegerEqualsParameterConstraint(notEqualTo);
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
