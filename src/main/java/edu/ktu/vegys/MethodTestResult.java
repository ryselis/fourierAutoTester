package edu.ktu.vegys;

public class MethodTestResult {

    private final String methodName;
    private final int numInvocations;
    private final int numCorrectResults;

    public MethodTestResult(String methodName, int numInvocations, int numCorrectResults) {
        this.methodName = methodName;
        this.numInvocations = numInvocations;
        this.numCorrectResults = numCorrectResults;
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
}
