package edu.ktu.vegys;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FourierAutoTester {

    public static void main(String[] args) throws IOException, ReflectiveOperationException, CodeCoverageException {
        MutantTester mutantTester = new MutantTester();
        List<MutantTestResult> mutantTestResults = new ArrayList<>();
        for (File mutantDirectory : new File("mutants").listFiles()) {
            mutantTestResults.add(mutantTester.test(mutantDirectory));
        }
    }
}
