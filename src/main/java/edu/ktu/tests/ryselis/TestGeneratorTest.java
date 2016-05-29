package edu.ktu.tests.ryselis;

import edu.ktu.ryselis.Parameter;
import edu.ktu.ryselis.Solution;
import edu.ktu.ryselis.TestGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Created by ryselis on 16.5.19.
 */
public class TestGeneratorTest {
    @Test
    
    public void generate() throws Exception {
        File f = new File("src/edu/ktu/tests/ryselis/FFT.java");
        TestGenerator generator = new TestGenerator(FFT.class.getMethod("fft", Complex[].class), f.getAbsolutePath());
        Collection<Solution> generated = generator.generate();
        boolean hasSize1Element = false;
        boolean hasOddLengthElement = false;
        boolean hasEvenLengthElement = false;
        for (Solution solution : generated) {
            for (Parameter parameter : solution.getParameters()) {
                Object[] value = (Object[]) parameter.getValue();
                int valueLength = value.length;
                if (valueLength == 1){
                    hasSize1Element = true;
                }
                if (valueLength != 1 && valueLength % 2 == 1){
                    hasOddLengthElement = true;
                }
                if (valueLength % 2 == 0){
                    hasEvenLengthElement = true;
                }
            }
        }
        Assert.assertTrue("Generated tests do not contain array of size 1", hasSize1Element);
        Assert.assertTrue("Generated tests do not contain array of odd size", hasOddLengthElement);
        Assert.assertTrue("Generated tests do not contain array of even size", hasEvenLengthElement);
    }

    @Test
    public void testGenerateOnIfft() throws Exception{
        File f = new File("src/edu/ktu/tests/ryselis/FFT.java");
        TestGenerator generator = new TestGenerator(FFT.class.getMethod("ifft", Complex[].class), f.getAbsolutePath());
        Collection<Solution> generated = generator.generate();
        Assert.assertTrue(generated.size() > 0);
    }

    @Test
    public void testGenerateOnConvolve() throws Exception{
        File f = new File("src/edu/ktu/tests/ryselis/FFT.java");
        TestGenerator generator = new TestGenerator(FFT.class.getMethod("cconvolve", Complex[].class, Complex[].class), f.getAbsolutePath());
        Collection<Solution> generated = generator.generate();
        boolean hasParametersOfEqualLength = false;
        boolean hasParametersOfDifferentLengths = false;
        for (Solution solution: generated){
            List<Parameter> parameters = (List<Parameter>) solution.getParameters();
            Object[] parameter1 = (Object[]) parameters.get(0).getValue();
            Object[] parameter2 = (Object[]) parameters.get(1).getValue();
            if (parameter1.length == parameter2.length){
                hasParametersOfEqualLength = true;
            }
            if (parameter2.length != parameter1.length){
                hasParametersOfDifferentLengths = true;
            }
        }
        Assert.assertTrue("Generated tests do not contain arrays of equal lengths", hasParametersOfEqualLength);
        Assert.assertTrue("Generated tests do not contain arrays of different lengths", hasParametersOfDifferentLengths);
    }
}