package edu.ktu.ryselis;

import java.util.Collection;

/**
 * Created by ryselis on 16.5.14.
 */
public class Solution {
    private Collection<Parameter> parameters;

    Solution(Collection<Parameter> params){
        parameters = params;
    }

    public Collection<Parameter> getParameters() {
        return parameters;
    }
}
