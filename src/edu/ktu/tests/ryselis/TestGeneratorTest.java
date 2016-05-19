package edu.ktu.tests.ryselis;

import edu.ktu.ryselis.Solution;
import edu.ktu.ryselis.TestGenerator;
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
        System.out.println(generated);
    }

}