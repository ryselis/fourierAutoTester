package edu.ktu.tests.ryselis;

import edu.ktu.ryselis.CodeParser;
import edu.ktu.ryselis.Method;
import edu.ktu.ryselis.parameterConstraints.ParameterConstraint;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

/**
 * Created by ryselis on 16.5.18.
 */
public class CodeParserTest {
    @Test
    public void getBranches() throws Exception {
        Class complexClass = FFT.class;
        File f = new File("src/edu/ktu/tests/ryselis/FFT.java");
        Method method = new Method(complexClass.getMethod("fft", Complex[].class), f.getAbsolutePath());
        CodeParser parser = new CodeParser(method);
        Collection<ParameterConstraint> branches = parser.getBranches();
        Assert.assertEquals(2, branches.size());
    }

}