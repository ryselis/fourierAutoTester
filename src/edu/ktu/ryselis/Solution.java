package edu.ktu.ryselis;

import java.util.Collection;

/**
 * Created by ryselis on 16.5.14.
 */
class Solution {
    private Collection<Parameter> parameters;

    Solution(Collection<Parameter> params){
        parameters = params;
    }

    Collection<Parameter> getParameters() {
        return parameters;
    }
}