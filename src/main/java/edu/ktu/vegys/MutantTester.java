package edu.ktu.vegys;

import edu.ktu.petkus.Coverage;
import edu.ktu.ryselis.Parameter;
import edu.ktu.ryselis.Solution;
import edu.ktu.ryselis.TestGenerator;
import edu.ktu.stuliene.Oracle;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
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
        for (Method method : mutantClass.getMethods()) {
            if ("main".equals(method.getName())) {
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

    private MethodTestResult testMethod(Method method, Object mutantObject, File mutantJavaFile) throws ReflectiveOperationException {
        TestGenerator inputGenerator = new TestGenerator(method, mutantJavaFile.getAbsolutePath());
        Collection<Solution> inputs = inputGenerator.generate();
        int numCorrectResults = 0;
        for (Solution input : inputs) {
            Parameter[] params = input.getParameters().toArray(new Parameter[]{});
            Object[] paramsObjects = input.getParameters().stream().map(Parameter::getValue).toArray();
            Object[] resultObjects = null;
            boolean exceptionOccurred = false;
            try {
                resultObjects = (Object[]) method.invoke(mutantObject, paramsObjects);
            } catch (Exception ex) {
                exceptionOccurred = true;
            }
            if (exceptionOccurred) {
                continue;
            }
            Parameter[] result = Arrays.stream(resultObjects)
                    .map(resultObject -> new Parameter(resultObject, null))
                    .toArray(Parameter[]::new);
            if (new Oracle().checkResult(params, result)) {
                numCorrectResults++;
            }
        }
        return new MethodTestResult(method.getName(), inputs.size(), numCorrectResults);
    }
}
