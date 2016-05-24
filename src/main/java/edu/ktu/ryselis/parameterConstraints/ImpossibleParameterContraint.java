package edu.ktu.ryselis.parameterConstraints;

/**
 * Created by ryselis on 16.5.25.
 */
public class ImpossibleParameterContraint implements ParameterConstraint {
    @Override
    public double validateValue(Object value) {
        return 1;
    }

    @Override
    public ParameterConstraint getInverse() {
        return new EverythingOkayParameterConstraint();
    }

    @Override
    public boolean acceptsSingleValue() {
        return true;
    }
}
