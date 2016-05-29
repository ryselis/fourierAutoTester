package edu.ktu.vegys;

public class MethodTestResult {

    private final String methodName;
    private final int numInvocations;
    private final int numCorrectResults;
    private final double coverage;

    public MethodTestResult(String methodName, int numInvocations, int numCorrectResults, double coverage) {
        this.methodName = methodName;
        this.numInvocations = numInvocations;
        this.numCorrectResults = numCorrectResults;
        this.coverage = coverage;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getNumInvocations() {
        return numInvocations;
    }

    public int getNumCorrectResults() {
        return numCorrectResults;
    }

    public double getCoverage() {
        return coverage;
    }
}
