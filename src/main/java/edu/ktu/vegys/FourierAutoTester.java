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
        for (File mutantDirectory : new File("mutants").listFiles()) {
            mutantTestResults.add(mutantTester.test(mutantDirectory));
        }
        for (int i = 0; i < mutantTestResults.size(); i++) {
            MutantTestResult mutantTestResult = mutantTestResults.get(i);
            System.out.print(i+1 + "\t");
            System.out.print(mutantTestResult.getMutantName() + "\t");
            System.out.print(mutantTestResult.getMethodTestResults().size() + "\t");
            boolean allTestsPassed = true;
            for (MethodTestResult methodTestResult : mutantTestResult.getMethodTestResults()) {
                if (methodTestResult.getNumCorrectResults() !=  methodTestResult.getNumInvocations()){
                    allTestsPassed = false;
                    break;
                }
            }
            if (allTestsPassed){
                System.out.print("good\t");
            } else {
                System.out.print("mutant\t");
            }
            System.out.print("Coverage not working yet");
            System.out.println();
        }
    }
}
