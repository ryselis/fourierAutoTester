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
        return distanceFromMax > distanceFromMax ? distanceFromMax : distanceFromZero;
    }

    @Override
    public ParameterConstraint getInverse() {
        return new ModuloNotEqualsParameterConstraint(moduloEqualTo);
    }
}
