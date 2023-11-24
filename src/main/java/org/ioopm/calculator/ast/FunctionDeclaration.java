package org.ioopm.calculator.ast;

import org.ioopm.calculator.ast.visitor.Visitor;

import java.util.ArrayList;

public class FunctionDeclaration extends SymbolicExpression {
    private String name;
    private ArrayList<String> parameters;
    private Sequence body = new Sequence();

    public FunctionDeclaration(String name, ArrayList<String> parameters) {
        super();
        this.name = name;
        this.parameters = parameters;
    }

    public void addToBody(SymbolicExpression expression) {
        body.add(expression);
    }

    public void setBody(Sequence body) {
        this.body = body;
    }

    @Override
    public int getPriority() {
        return 1_000_000_00;
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("function ").append(name).append("(");
        for (int i = 0; i < parameters.size(); i++) {
            sb.append(parameters.get(i));
            if (i != parameters.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(")\n");
        for (SymbolicExpression statement : body.getStatements()) {
            sb.append(statement).append('\n');
        }
        return sb.append("end").toString();
    }

    @Override
    public boolean equals(Object obj) {
        //TODO: Fix
        if (obj instanceof FunctionDeclaration other) {
            return this.name.equals(other.name)
                    && this.parameters.equals(other.parameters)
                    && this.body.equals(other.body);
        }

        return false;
    }

    @Override
    public String getName() {
        return name;
    }
}
