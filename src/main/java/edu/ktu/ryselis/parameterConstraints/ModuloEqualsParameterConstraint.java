package edu.ktu.ryselis.parameterConstraints;

/**
 * Created by ryselis on 16.5.18.
 */
public class ModuloEqualsParameterConstraint implements ParameterConstraint {
    private int moduloEqualTo;

    public ModuloEqualsParameterConstraint(int moduloEqualTo) {
        this.moduloEqualTo = moduloEqualTo;
    }

    @Override
    public double validateValue(Object value) {
        int val = (Integer) value;
        int remainder = val % moduloEqualTo;
        int distanceFromZero = remainder;
        int distanceFromMax = moduloEqualTo - remainder;
        return (distanceFromMax < distanceFromZero ? distanceFromMax : distanceFromZero) / moduloEqualTo;
    }

    @Override
    public ParameterConstraint getInverse() {
        return new ModuloNotEqualsParameterConstraint(moduloEqualTo);
    }

    @Override
    public boolean acceptsSingleValue() {
        return true;
    }

    @Override
    public boolean compliesWithPowerOf2Constraint() {
        return (moduloEqualTo & (moduloEqualTo - 1)) == 0;
    }
}
