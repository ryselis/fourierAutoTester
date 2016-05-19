package edu.ktu.ryselis;

import edu.ktu.ryselis.parameterConstraints.ParameterConstraint;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ryselis on 16.5.18.
 */
public class ParameterConstraintGenerator{
    private Collection<ParameterConstraint> initialConstraints;
    private int currentConstraint;
    private int maxConstraint;

    public ParameterConstraintGenerator(Collection<ParameterConstraint> initialConstraints) {
        this.initialConstraints = initialConstraints;
        currentConstraint = 0;
        maxConstraint = 1;
        for (int i = 0; i < initialConstraints.size(); i++){
            maxConstraint *= 2;
        }
    }

    public Collection<ParameterConstraint> next(){
        if (currentConstraint < maxConstraint){
            Collection<ParameterConstraint> newConstraints = new ArrayList<>(initialConstraints.size());
            int i = 0;
            for (ParameterConstraint initialConstraint : initialConstraints) {
                int currentState = (currentConstraint >> i) % 2;
                if (currentState == 0) {
                    newConstraints.add(initialConstraint);
                } else {
                    newConstraints.add(initialConstraint.getInverse());
                }
                i++;
            }
            currentConstraint++;
            return newConstraints;
        } else {
            return null;
        }
    }
}
