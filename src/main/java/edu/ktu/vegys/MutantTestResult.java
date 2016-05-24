package edu.ktu.vegys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MutantTestResult {

    private final String mutantName;
    private final List<MethodTestResult> methodTestResults = new ArrayList<>();

    public MutantTestResult(String mutantName) {
        this.mutantName = mutantName;
    }

    public String getMutantName() {
        return mutantName;
    }

    public void addMethodTestResult(MethodTestResult methodTestResult) {
        methodTestResults.add(methodTestResult);
    }

    public List<MethodTestResult> getMethodTestResults() {
        return Collections.unmodifiableList(methodTestResults);
    }
}
