package edu.ktu.test.petkus;

import edu.ktu.petkus.Coverage;
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
            coverager.BeginCoverage(target);
            int res = target.method1(0);
            coverager.EndCoverage();
        }
        catch (Exception e){

        }
    }
}
