package edu.ktu.test1.stuliene;

import edu.ktu.ryselis.Parameter;
import edu.ktu.tests.ryselis.Complex;
import edu.ktu.tests.ryselis.FFT;
import edu.ktu.stuliene.Oracle;
import org.junit.Test;

/**
 * Created by Aiste on 2016.05.20.
 */
public class OracleTest {

    @Test
    public void testOracle()throws Exception {

        Complex[] x = new Complex[10];
        for (int i = 0; i < x.length; i++) {
            x[i] = new Complex(i, 0);
            x[i] = new Complex(-2*Math.random() + 1, 0);
        }
        Parameter x1 = new Parameter(x, null);

        Complex[] y = FFT.fft(x);
        Parameter y1 = new Parameter(y, null);

        Complex[] z = FFT.ifft(y);
        Parameter z1 = new Parameter(z, null);

        Complex[] c = FFT.cconvolve(x, x);
        Parameter c1 = new Parameter(c, null);

        Complex[] d = FFT.convolve(x, x);
        Parameter d1 = new Parameter(d, null);

        Oracle oracle1 = new Oracle();
        oracle1.checkResult(x1, y1);
        oracle1.checkResult(x1, z1);
        oracle1.checkResult(x1, c1);
        oracle1.checkResult(x1, d1);
    }

}
