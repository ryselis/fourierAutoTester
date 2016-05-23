package edu.ktu.ryselis.parameterConstraints;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;

/**
 * Created by ryselis on 16.5.18.
 */
public class ParameterConstraintFactory {
    public static ParameterConstraint build(Expression expression){
        if (expression instanceof BinaryExpr){
            BinaryExpr binaryExpression = (BinaryExpr) expression;
            BinaryExpr.Operator operator = binaryExpression.getOperator();
            String operatorName = operator.name();
            if (operatorName.equals("equals")){
                IntegerLiteralExpr rhs = (IntegerLiteralExpr) binaryExpression.getRight();
                return new ArrayLengthParameterConstraint(new IntegerEqualsParameterConstraint(Integer.parseInt(rhs.getValue())));
            }
            if (operatorName.equals("notEquals")){
                if (binaryExpression.getLeft() instanceof BinaryExpr){
                    BinaryExpr lhsExpr = (BinaryExpr) binaryExpression.getLeft();
                    if (lhsExpr.getOperator().name().equals("remainder")){
                        IntegerLiteralExpr right = (IntegerLiteralExpr) lhsExpr.getRight();
                        return new ArrayLengthParameterConstraint(new ModuloNotEqualsParameterConstraint(Integer.parseInt(right.getValue())));
                    }
                }
                if (binaryExpression.getLeft() instanceof FieldAccessExpr){
                    return new ArrayLengthNotEqualParameterConstraint();
                }
            }
        }
        return null;
    }
}
