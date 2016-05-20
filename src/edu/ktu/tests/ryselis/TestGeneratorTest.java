package edu.ktu.tests.ryselis;

import edu.ktu.ryselis.Parameter;
import edu.ktu.ryselis.Solution;
import edu.ktu.ryselis.TestGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

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

}