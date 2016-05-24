package edu.ktu.ryselis;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import edu.ktu.ryselis.parameterConstraints.ParameterConstraint;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by ryselis on 16.5.18.
 */
public class CodeParser {
    private Method method;

    public CodeParser(Method method) {
        this.method = method;
    }

    public Collection<ParameterConstraint> getBranches() {
        CompilationUnit compilationUnit = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(method.getPathToClassSource());
        } catch (FileNotFoundException ignored) {
            throw new RuntimeException(ignored);
        }
        if (inputStream == null){
            return null;
        }
        try {
            compilationUnit = JavaParser.parse(inputStream);
        } catch (ParseException ignored) {
            throw new RuntimeException(ignored);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ignored) {
            }
        }
        if (compilationUnit == null) {
            return null;
        }
        MethodVisitor visitor = new MethodVisitor(method);
        visitor.visit(compilationUnit, null);
        Collection<ParameterConstraint> parameterConstraints = visitor.getParameterConstraints();
        if (parameterConstraints == null){
            return null;
        }
        return parameterConstraints;
    }
}
