package edu.ktu.petkus;

import edu.ktu.vegys.CodeCoverageException;
import edu.ktu.vegys.MutantTestResult;
import edu.ktu.vegys.MutantTester;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tautvydas on 5/24/2016.
 */
public class MutantTestHolder implements Runnable {
    @Override
    public void run() {
        MutantTester mutantTester = new MutantTester();
        List<MutantTestResult> mutantTestResults = new ArrayList<>();
        File mutantDirectory = ActiveMutant.ActiveMutant;
        try {
            mutantTestResults.add(mutantTester.test(mutantDirectory));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        } catch (CodeCoverageException e) {
            e.printStackTrace();
        }
    }
}
