package edu.ktu.tests.ryselis;

import edu.ktu.ryselis.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ryselis on 16.5.14.
 */
public class ObjectGeneratorTest {
    @Test
    public void constructNewParameter() throws Exception {
        Complex complex = new Complex(0, 0);
        Parameter<Complex> parameter = new Parameter(complex, new Object[]{0.0, 0.0});
        ObjectGenerator chooser = new ObjectGenerator();
        Parameter<Complex> parameter1 = chooser.constructNewParameter(parameter);
        Assert.assertTrue("Real part is wrong, got " + parameter1.getValue(), Math.abs(parameter1.getValue().Re()) < Double.MAX_VALUE / 1000000);
        Assert.assertTrue("Imaginary part is wrong, got " + parameter1.getValue(), Math.abs(parameter1.getValue().Im()) < Double.MAX_VALUE / 1000000);
    }

    @Test
    public void ConstructNewParameterForComplexArray(){
        Complex[] complex = new Complex[4];
        Object[][] constructorArgs = new Object[complex.length][];
        for (int i = 0; i < complex.length; i++){
            complex[i] = new Complex(0, 0);
            constructorArgs[i] = new Object[] {0.0, 0.0};
        }
        Parameter<Complex[]> parameter = new Parameter<>(complex, constructorArgs);
        ObjectGenerator chooser = new ObjectGenerator();
        Parameter<Object[]> parameter1 = chooser.constructNewParameter(parameter);
        for (int i = 0; i < complex.length; i++){
            Complex complex1 = (Complex) parameter1.getValue()[i];
            Assert.assertTrue("Real part is worng, got " + complex1.Re(), Math.abs(complex1.Re()) < Double.MAX_VALUE / 1000000);
            Assert.assertTrue("Imaginary part is worng, got " + complex1.Im(), Math.abs(complex1.Im()) < Double.MAX_VALUE / 1000000);
        }
    }

    @Test
    public void constructNewParametersForStartingSolutionFromArray(){
        ParameterDefinition parameterDefinition = new ParameterDefinition(Complex[].class);
        ObjectGenerator chooser = new ObjectGenerator();
        Parameter parameter = chooser.constructRandomParameter(parameterDefinition);
        Assert.assertEquals("Wrong parameter class", parameter.getValue().getClass(), Complex[].class);
    }

}