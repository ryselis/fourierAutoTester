package edu.ktu.petkus;

import edu.ktu.test.petkus.CoverageTargets;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryselis on 16.5.14.
 */
public class Coverage {
    StackTraceElement[] initialTrace;
    Class monitoredClass;

    public void BeginCoverage(Class c){
        initialTrace = Thread.currentThread().getStackTrace();
        monitoredClass = c;
    }

    public void EndCoverage(Class c){
        List<StackTraceElement> classStackTrace = TrimInitialFromTrace();
//        for(int i = 0; i < trace.length; i++){
//            if (trace[i].getClassName() == monitoredClass.getName()){
//                classStackTrace.add(trace[i]);
//            }
//        }
    }

    public List<StackTraceElement> TrimInitialFromTrace(){
        List<StackTraceElement> res = new ArrayList<StackTraceElement>();
        StackTraceElement[] currentTrace = Thread.currentThread().getStackTrace();
        for (int j=0;j<currentTrace.length;j++){
            boolean duplicate = false;
            for (int k=j+1;k<initialTrace.length;k++)
                if (k!=j && initialTrace[k] == currentTrace[j])
                    duplicate = true;
            if (!duplicate)
                res.add(currentTrace[j]);
        }
        return res;
    }


}
