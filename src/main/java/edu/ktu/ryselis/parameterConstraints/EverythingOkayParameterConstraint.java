package edu.ktu.ryselis.parameterConstraints;

/**
 * Created by ryselis on 16.5.25.
 */
public class EverythingOkayParameterConstraint implements ParameterConstraint {
    @Override
    public double validateValue(Object value) {
        return 0;
    }

    @Override
    public ParameterConstraint getInverse() {
        return new ImpossibleParameterContraint();
    }

    @Override
    public boolean acceptsSingleValue() {
        return true;
    }
}
