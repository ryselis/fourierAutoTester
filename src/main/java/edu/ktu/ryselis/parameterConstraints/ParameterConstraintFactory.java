package edu.ktu.ryselis.parameterConstraints;

import com.github.javaparser.ast.expr.*;

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
                if (binaryExpression.getLeft() instanceof NameExpr){
                    IntegerLiteralExpr rhs = (IntegerLiteralExpr) binaryExpression.getRight();
                    return new ArrayLengthParameterConstraint(new IntegerNotEqualsParameterConstraint(Integer.parseInt(rhs.getValue())));
                }
            }
            if (operatorName.equals("greater")){
                IntegerLiteralExpr rhs = (IntegerLiteralExpr) binaryExpression.getRight();
                return new ArrayLengthParameterConstraint(new IntegerGreaterThanParameterConstraint(Integer.parseInt(rhs.getValue())));
            }
            if (operatorName.equals("lessEquals")){
                if (binaryExpression.getLeft() instanceof BinaryExpr){
                    BinaryExpr lhsExpr = (BinaryExpr) binaryExpression.getLeft();
                    if (lhsExpr.getOperator().name().equals("remainder")){
                        if (binaryExpression.getRight() instanceof IntegerLiteralExpr){
                            IntegerLiteralExpr right = (IntegerLiteralExpr) binaryExpression.getRight();
                            if (Integer.parseInt(right.getValue()) == 0){
                                return new ArrayLengthParameterConstraint(new ImpossibleParameterContraint());
                            }
                        }
                    }
                }
                if (binaryExpression.getLeft() instanceof NameExpr){
                    IntegerLiteralExpr rhs = (IntegerLiteralExpr) binaryExpression.getRight();
                    return new ArrayLengthParameterConstraint(new IntegerLessOrEqualTo(Integer.parseInt(rhs.getValue())));
                }
            }
            if (operatorName.equals("greaterEquals")){
                if (binaryExpression.getLeft() instanceof BinaryExpr){
                    BinaryExpr lhsExpr = (BinaryExpr) binaryExpression.getLeft();
                    if (lhsExpr.getOperator().name().equals("remainder")){
                        if (binaryExpression.getRight() instanceof IntegerLiteralExpr){
                            IntegerLiteralExpr right = (IntegerLiteralExpr) binaryExpression.getRight();
                            if (Integer.parseInt(right.getValue()) == 0){
                                return new ArrayLengthParameterConstraint(new EverythingOkayParameterConstraint());
                            }
                        }
                    }
                } else if (binaryExpression.getLeft() instanceof NameExpr){
                    if (binaryExpression.getRight() instanceof IntegerLiteralExpr) {
                        IntegerLiteralExpr right = (IntegerLiteralExpr) binaryExpression.getRight();
                        return new ArrayLengthParameterConstraint(new IntegerGreaterThanOrEqualsParameterContraint(Integer.parseInt(right.getValue())));
                    }
                }
            }
            if (operatorName.equals("less")){
                if (binaryExpression.getLeft() instanceof NameExpr) {
                    if (binaryExpression.getRight() instanceof IntegerLiteralExpr) {
                        IntegerLiteralExpr right = (IntegerLiteralExpr) binaryExpression.getRight();
                        return new ArrayLengthParameterConstraint(new IntegerLessThanParameterConstraint(Integer.parseInt(right.getValue())));
                    }
                }
                if (binaryExpression.getLeft() instanceof BinaryExpr){
                    BinaryExpr lhsExpr = (BinaryExpr) binaryExpression.getLeft();
                    if (lhsExpr.getOperator().name().equals("remainder")){
                        return new ArrayLengthParameterConstraint(new ImpossibleParameterContraint());
                    }
                }
            }

        }
        return null;
    }
}
