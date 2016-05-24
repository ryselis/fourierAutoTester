package edu.ktu.petkus;

import edu.ktu.test.petkus.CoverageTargets;

/**
 * Created by Tautvydas on 5/21/2016.
 */
public class TestHolder implements Runnable {
    CoverageTargets target = new CoverageTargets();

    @Override
    public void run() {
        target.method1(0);
        target.method1(1);
        target.method1(-1);
        target.method2(0);
        target.method2(1);
        target.method2(2);
        target.method2(3);
    }
}
