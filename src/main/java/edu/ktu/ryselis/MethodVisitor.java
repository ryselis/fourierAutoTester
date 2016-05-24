package edu.ktu.ryselis;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.ktu.ryselis.parameterConstraints.ParameterConstraint;
import edu.ktu.ryselis.parameterConstraints.ParameterConstraintFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ryselis on 16.5.18.
 */
public class MethodVisitor extends VoidVisitorAdapter {
    private Method method;
    private Collection<ParameterConstraint> parameterConstraints;

    public MethodVisitor(Method method) {
        this.method = method;
    }

    @Override
    public void visit(MethodDeclaration n, Object arg) {
        if (method.getMethod().getName().equals(n.getName())) {
            Collection<IfStmt> ifStatements = getIfStatements(n);
            parameterConstraints = getCodePaths(ifStatements);
        }
        super.visit(n, arg);
    }

    private Collection<IfStmt> getIfStatements(MethodDeclaration methodDeclaration){
        List<Node> childrenNodes = methodDeclaration.getChildrenNodes();
        Collection<IfStmt> ifStatements = new ArrayList<>();
        for (Node node : childrenNodes){
            if (node instanceof BlockStmt){
                BlockStmt blockStatement = (BlockStmt) node;
                for (Statement statement : blockStatement.getStmts()){
                    if (statement instanceof IfStmt){
                        ifStatements.add((IfStmt) statement);
                    }
                }
            }
        }
        return ifStatements;
    }

    private Collection<ParameterConstraint> getCodePaths(Collection<IfStmt> ifStatements){
        Collection<Expression> expressions = new ArrayList<>();
        for (IfStmt ifStmt: ifStatements){
            Expression condition = ifStmt.getCondition();
            expressions.add(condition);
        }
        Collection<ParameterConstraint> parameterConstraints = new ArrayList<>();
        for (Expression expression : expressions){
            parameterConstraints.add(ParameterConstraintFactory.build(expression));
        }
        return parameterConstraints;
    }

    public Collection<ParameterConstraint> getParameterConstraints() {
        return parameterConstraints;
    }
}
