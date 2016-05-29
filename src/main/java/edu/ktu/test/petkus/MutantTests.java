package edu.ktu.test.petkus;

import edu.ktu.petkus.ActiveMutant;
import edu.ktu.petkus.Coverage;
import edu.ktu.petkus.MutantTestHolder;
import edu.ktu.tests.ryselis.FFT;
import edu.ktu.vegys.MutantTestResult;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tautvydas on 5/24/2016.
 */
public class MutantTests {
    @Test
    public void TestAllMutants(){
        Coverage coverager = new Coverage(System.out);
        try{
            List<MutantTestResult> mutantTestResults = new ArrayList<>();
            for (File mutantDirectory : new File("mutants").listFiles()) {
                ActiveMutant.ActiveMutant = mutantDirectory;
                double coverage = coverager.Cover(FFT.class, MutantTestHolder.class, "");
                System.out.print(coverage);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
