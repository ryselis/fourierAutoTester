package edu.ktu.tests.ryselis;

import edu.ktu.ryselis.NeighbourChooser;
import edu.ktu.ryselis.Parameter;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ryselis on 16.5.14.
 */
public class NeighbourChooserTest {
    @Test
    public void constructNewParameter() throws Exception {
        Complex complex = new Complex(0, 0);
        Parameter<Complex> parameter = new Parameter(complex, new Object[]{0.0, 0.0});
        NeighbourChooser chooser = new NeighbourChooser(parameter);
        Parameter<Complex> parameter1 = chooser.constructNewParameter();
        Assert.assertTrue("Real part is wrong, got " + parameter1.getValue(), Math.abs(parameter1.getValue().Re()) < Double.MAX_VALUE / 1000000);
        Assert.assertTrue("Imaginary part is wrong, got " + parameter1.getValue(), Math.abs(parameter1.getValue().Im()) < Double.MAX_VALUE / 1000000);
    }

}