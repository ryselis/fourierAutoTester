package edu.ktu.petkus;

import org.jacoco.core.analysis.*;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.core.instr.Instrumenter;
import org.jacoco.core.runtime.IRuntime;
import org.jacoco.core.runtime.LoggerRuntime;
import org.jacoco.core.runtime.RuntimeData;
import org.objectweb.asm.ClassReader;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Collection;

public class Coverage {
    private final PrintStream out;
    private String targetName = "";
    private RuntimeData data;
    private IRuntime runtime;
    public Coverage(final PrintStream out) {
        this.out = out;
    }

    public double Cover(Class c, Class testHolder, String mutantPackage) throws Exception {
        double res = 0;
        targetName = c.getName();
        String holderName = testHolder.getName();

        // For instrumentation and runtime we need a IRuntime instance
        // to collect execution data:
        runtime = new LoggerRuntime();

        // The Instrumenter creates a modified version of our test target class
        // that contains additional probes for execution data recording:
        final Instrumenter instr = new Instrumenter(runtime);
//        final byte[] instrumented = instr.instrument(c.getClassLoader().getResourceAsStream(c.getName() + ".java"), targetName).rep);
        final byte[] instrumented;
        if (mutantPackage == ""){
            instrumented = instr.instrument(getTargetClass(targetName), targetName);
        }
        else{
//            instrumented = instr.instrument(getTargetClassByObject(c), c.getName());
            instrumented = instr.instrument(getTargetClassByObject(c), targetName);
        }


        // Now we're ready to run our instrumented class and need to startup the
        // runtime first:
        data = new RuntimeData();
        runtime.startup(data);

        // In this tutorial we use a special class loader to directly load the
        // instrumented class definition from a byte[] instances.
        final MemoryClassLoader memoryClassLoader = new MemoryClassLoader();
        memoryClassLoader.addDefinition(targetName, instrumented);
        final Class<?> targetClass = memoryClassLoader.loadClass(targetName);
        final Class<?> holderClass = memoryClassLoader.loadClass(holderName);

        for (Method method : targetClass.getDeclaredMethods()) {
            if (!"fft".equals(method.getName())) {
                continue;
            }
            MethodInvokerStaticParametersHolder.method = method;
        }
        // Here we execute our test target class through its Runnable interface:
        MethodInvokerStaticParametersHolder.mutantObject = targetClass.newInstance();
        final Runnable holderInstance = (Runnable) holderClass.newInstance();
        holderInstance.run();

        final ExecutionDataStore executionData = new ExecutionDataStore();
        final SessionInfoStore sessionInfos = new SessionInfoStore();
        data.collect(executionData, sessionInfos, false);
        runtime.shutdown();

        // Together with the original class definition we can calculate coverage
        // information:
        final CoverageBuilder coverageBuilder = new CoverageBuilder();
        final Analyzer analyzer = new Analyzer(executionData, coverageBuilder);
        if (mutantPackage == ""){
            analyzer.analyzeClass(getTargetClass(targetName), targetName);
        }
        else{
//            analyzer.analyzeClass(getTargetClassByObject(FFT.class), c.getName());
            analyzer.analyzeClass(getTargetClassByObject(c), targetName);
        }
        // Let's dump some metrics and line coverage information:
        for (final IClassCoverage cc : coverageBuilder.getClasses()) {
            Collection<IMethodCoverage> methods = cc.getMethods();
            for (IMethodCoverage methodCoverage : methods ){
                String methodName = methodCoverage.getName();
                if (methodName.equals("fft")){
                    int missedCount = methodCoverage.getLineCounter().getMissedCount();
                    int totalCount = methodCoverage.getLineCounter().getTotalCount();
                    int hitCount = methodCoverage.getLineCounter().getCoveredCount();

                    res = (((hitCount) * 100) / totalCount);
                }
            }
//            out.printf("Coverage of class %s%n", cc.getName());

//            printCounter("instructions", cc.getInstructionCounter());
//            printCounter("branches", cc.getBranchCounter());
//            printCounter("lines", cc.getLineCounter());
//            printCounter("methods", cc.getMethodCounter());
//            printCounter("complexity", cc.getComplexityCounter());

//            for (int i = cc.getFirstLine(); i <= cc.getLastLine(); i++) {
//                out.printf("Line %s: %s%n", Integer.valueOf(i));
//            }

        }
        return res;
    }

    public InputStream getTargetClassByObject(Class c) throws IOException {
        final String resource = c.getSimpleName() + ".class";
        return c.getResourceAsStream(resource);
    }

    public InputStream getTargetClass(final String name) {
        final String resource = '/' + name.replace('.', '/') + ".class";
        return getClass().getResourceAsStream(resource);
    }

    private void printCounter(final String unit, final ICounter counter) {
        final Integer missed = Integer.valueOf(counter.getMissedCount());
        final Integer total = Integer.valueOf(counter.getTotalCount());
        out.printf("%s of %s %s missed%n", missed, total, unit);
    }
}
