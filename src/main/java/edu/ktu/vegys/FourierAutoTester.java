package edu.ktu.vegys;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FourierAutoTester {

    public static void main(String[] args) throws IOException, ReflectiveOperationException, CodeCoverageException {
        MutantTester mutantTester = new MutantTester();
        List<MutantTestResult> mutantTestResults = new ArrayList<>();
        mutantTestResults.add(mutantTester.test(new File("src/main/java/edu/ktu/tests/ryselis/realFFT")));
        for (File mutantDirectory : new File("src/main/java/edu/ktu/tests/ryselis/mutants").listFiles()) {
            mutantTestResults.add(mutantTester.test(mutantDirectory));
        }
        for (int i = 0; i < mutantTestResults.size(); i++) {
            MutantTestResult mutantTestResult = mutantTestResults.get(i);
            System.out.print(i+1 + "\t");
            System.out.print(mutantTestResult.getMutantName() + "\t");
            int invocations = 0;
            double coverage = 0;
            boolean allTestsPassed = true;
            for (MethodTestResult methodTestResult : mutantTestResult.getMethodTestResults()) {
                if (methodTestResult.getNumCorrectResults() !=  methodTestResult.getNumInvocations()){
                    allTestsPassed = false;
                }
                invocations+=methodTestResult.getNumInvocations();
                if (methodTestResult.getCoverage() > coverage){
                    coverage = methodTestResult.getCoverage();
                }
            }
            System.out.print(invocations + "\t");
            if (allTestsPassed){
                System.out.print("good\t");
            } else {
                System.out.print("mutant\t");
            }
            System.out.print("Coverage : "+coverage);
            System.out.println();
        }
    }
}
