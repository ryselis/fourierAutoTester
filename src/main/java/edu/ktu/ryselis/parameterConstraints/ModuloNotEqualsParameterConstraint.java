package edu.ktu.ryselis.parameterConstraints;

/**
 * Created by ryselis on 16.5.18.
 */
public class ModuloNotEqualsParameterConstraint implements ParameterConstraint {
    private int moduloNotEqualTo;

    public ModuloNotEqualsParameterConstraint(int moduloNotEqualTo) {
        this.moduloNotEqualTo = moduloNotEqualTo;
    }

    @Override
    public double validateValue(Object value) {
        int v = (Integer) value;
        return v % moduloNotEqualTo == 0 ? 1 : 0;
    }

    @Override
    public ParameterConstraint getInverse() {
        return new ModuloEqualsParameterConstraint(moduloNotEqualTo);
    }

    @Override
    public boolean acceptsSingleValue() {
        return true;
    }

    @Override
    public boolean compliesWithPowerOf2Constraint() {
        return (moduloNotEqualTo & (moduloNotEqualTo - 1)) != 0;
    }
}
