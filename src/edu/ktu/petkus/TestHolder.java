package edu.ktu.petkus;

import edu.ktu.test.petkus.CoverageTargets;

/**
 * Created by Tautvydas on 5/21/2016.
 */
public class TestHolder implements Runnable {
    CoverageTargets target = new CoverageTargets();

    @Override
    public void run() {
        int res = target.method1(0);
    }
}
