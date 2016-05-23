package edu.ktu.test1.stuliene;

import static java.lang.System.*;
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

        Complex[] x = new Complex[1];
        Parameter[] x1 = new Parameter[1];
        Parameter[] y1 = new Parameter[1];
        Parameter[] z1 = new Parameter[1];
        Parameter[] c1 = new Parameter[1];
        Parameter[] d1 = new Parameter[1];

        for (int i = 0; i < x.length; i++) {
            x[i] = new Complex(i, 0);
            x[i] = new Complex(-2*Math.random() + 1, 0);
            x1[i]= new Parameter(x[i], null);
        }


        Complex[] y = FFT.fft(x);

        Complex[] z = FFT.ifft(y);

        Complex[] c = FFT.cconvolve(x, x);

        Complex[] d = FFT.convolve(x, x);

        for (int i = 0; i < x.length; i++) {
            y1[i] = new Parameter(y[i], null);
            z1[i] = new Parameter(z[i], null);
            c1[i] = new Parameter(c[i], null);
            d1[i] = new Parameter(d[i], null);
        }

        Oracle oracle1 = new Oracle();
        oracle1.checkResult(x1, y1);
        oracle1.checkResult(x1, z1);
        oracle1.checkResult(x1, c1);
        oracle1.checkResult(x1, d1);

    }

}
