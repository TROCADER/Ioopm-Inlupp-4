package org.ioopm.calculator.parser;

import org.ioopm.calculator.ast.SymbolicExpression;

public class IllegalExpressionException extends RuntimeException {
    SymbolicExpression expression;

    public IllegalExpressionException(String msg) {
        super(msg);
    }

    public IllegalExpressionException(String msg, SymbolicExpression expression) {
        super(msg);
        this.expression = expression;
    }
}