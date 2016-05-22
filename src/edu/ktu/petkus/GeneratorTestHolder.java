package edu.ktu.petkus;

import edu.ktu.ryselis.Parameter;
import edu.ktu.ryselis.Solution;
import edu.ktu.ryselis.TestGenerator;
import edu.ktu.tests.ryselis.Complex;
import edu.ktu.tests.ryselis.FFT;

import java.io.File;
import java.util.Collection;

/**
 * Created by Tautvydas on 5/22/2016.
 */
public class GeneratorTestHolder implements Runnable {
    @Override
    public void run() {
        File f = new File("src/edu/ktu/tests/ryselis/FFT.java");
        TestGenerator generator = null;
        try {
            generator = new TestGenerator(FFT.class.getMethod("fft", Complex[].class), f.getAbsolutePath());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Collection<Solution> generated = generator.generate();
        for (Solution solution : generated) {
            for (Parameter parameter : solution.getParameters()) {
                Object[] value = (Object[]) parameter.getValue();
                Complex[] c = new Complex[value.length];
                int i = 0;
                for(Object obj: value){
                    c[i] = (Complex) obj;
                    i++;
                }
                try{
                    Complex[] result = FFT.fft( c );
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
}
