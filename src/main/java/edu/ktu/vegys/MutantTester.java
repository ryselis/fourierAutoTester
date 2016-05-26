package edu.ktu.vegys;

import edu.ktu.petkus.Coverage;
import edu.ktu.petkus.MethodInvoker;
import edu.ktu.petkus.MethodInvokerStaticParametersHolder;
import edu.ktu.ryselis.Parameter;
import edu.ktu.ryselis.Solution;
import edu.ktu.ryselis.TestGenerator;
import edu.ktu.stuliene.Oracle;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;

public class MutantTester {

    private static final String MUTANT_CLASS_NAME = "FFT";

    public MutantTestResult test(File mutantDirectory) throws IOException, ReflectiveOperationException, CodeCoverageException {
        File mutantClassFile = new File(mutantDirectory, MUTANT_CLASS_NAME + ".class");
        Class<?> mutantClass = loadClass(mutantClassFile, MUTANT_CLASS_NAME);
        Object mutantObject = mutantClass.newInstance();
        File mutantJavaFile = new File(mutantDirectory, MUTANT_CLASS_NAME + ".java");
        MutantTestResult mutantTestResult = new MutantTestResult(mutantDirectory.getName());
        Coverage codeCoverage = new Coverage(System.out);
//        try {
//            codeCoverage.BeginCoverage(mutantObject, mutantClassFile.toURI().toURL());
//        } catch (Exception ex) {
//            throw new CodeCoverageException(ex);
//        }
        for (Method method : mutantClass.getDeclaredMethods()) {
            if (!"fft".equals(method.getName())) {
                continue;
            }
            MethodTestResult methodTestResult = testMethod(method, mutantClass, mutantJavaFile);
            mutantTestResult.addMethodTestResult(methodTestResult);
        }
//        try {
//            codeCoverage.EndCoverage();
//        } catch (Exception ex) {
//            throw new CodeCoverageException(ex);
//        }
        return mutantTestResult;
    }

    private Class<?> loadClass(File mutantClassFile, String className) throws IOException, ReflectiveOperationException {
        URL directoryUrl = mutantClassFile.getParentFile().toURI().toURL();
        ClassLoader classLoader = new URLClassLoader(new URL[]{directoryUrl});
        return classLoader.loadClass(className);
    }

    private MethodTestResult testMethod(Method method, Class mutantObject, File mutantJavaFile) throws ReflectiveOperationException {
        System.out.println(mutantJavaFile);
        if (mutantJavaFile.getAbsolutePath().contains("AOIS_76")){
            int a = 1;
        }
        TestGenerator inputGenerator = new TestGenerator(method, mutantJavaFile.getAbsolutePath());
        Collection<Solution> inputs = inputGenerator.generate();
        int numCorrectResults = 0;
        for (Solution input : inputs) {
            Parameter[] params = input.getParameters().toArray(new Parameter[]{});
            Object[] paramsObjects = input.getParameters().stream().map(Parameter::getValue).toArray();
            Object[] resultObjects = null;
            boolean exceptionOccurred = false;

            MethodInvokerStaticParametersHolder.resultObjects = resultObjects;
            MethodInvokerStaticParametersHolder.exceptionOccurred = exceptionOccurred;
            MethodInvokerStaticParametersHolder.method = method;
            MethodInvokerStaticParametersHolder.mutantObject = mutantObject.newInstance();
            MethodInvokerStaticParametersHolder.paramsObjects = paramsObjects;

            Coverage coverager = new Coverage(System.out);
            double coverage = 0;
            try {
                coverage = coverager.Cover(mutantObject, MethodInvoker.class, mutantJavaFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }

            resultObjects = MethodInvokerStaticParametersHolder.resultObjects;
            exceptionOccurred = MethodInvokerStaticParametersHolder.exceptionOccurred;
            Object[] paramsToOracle = new Object[params.length];
            for (int i = 0; i < paramsToOracle.length; i++) {
                paramsToOracle[i] = params[i].getValue();
            }
            boolean resultIsGood = new Oracle().checkResult(paramsToOracle, resultObjects, exceptionOccurred);
            if (resultIsGood) {
                numCorrectResults++;
            }
            System.out.println(coverage);
            System.out.println(exceptionOccurred);
        }
        System.out.println(numCorrectResults == inputs.size());
        return new MethodTestResult(method.getName(), inputs.size(), numCorrectResults);
    }
}
