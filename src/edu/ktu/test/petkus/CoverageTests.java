package edu.ktu.test.petkus;

import edu.ktu.petkus.Coverage;
import edu.ktu.petkus.TestHolder;
import org.junit.Test;

import java.lang.invoke.MethodHandles;

/**
 * Created by Tautvydas on 5/17/2016.
 */
public class CoverageTests {
    @Test
    public void TestBasicFunctionality(){
        Coverage coverager = new Coverage(System.out);
        CoverageTargets target = new CoverageTargets();
        try{
            double coverage = coverager.Cover(CoverageTargets.class, TestHolder.class);
            System.out.print(coverage);
        }
        catch (Exception e){

        }
    }
}
