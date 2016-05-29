package edu.ktu.test.petkus;

import edu.ktu.petkus.Coverage;
import edu.ktu.petkus.GeneratorTestHolder;
import edu.ktu.petkus.TestHolder;
import edu.ktu.tests.ryselis.FFT;
import org.junit.Test;

/**
 * Created by Tautvydas on 5/17/2016.
 */
public class CoverageTests {
    @Test
    public void TestBasicFunctionality(){
        Coverage coverager = new Coverage(System.out);
        CoverageTargets target = new CoverageTargets();
        try{
            double coverage = coverager.Cover(CoverageTargets.class, TestHolder.class, "");
            System.out.print(coverage);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void TestFFTGenerator(){
        Coverage coverager = new Coverage(System.out);
        try{
            double coverage = coverager.Cover(FFT.class, GeneratorTestHolder.class, "");
            System.out.print(coverage);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
