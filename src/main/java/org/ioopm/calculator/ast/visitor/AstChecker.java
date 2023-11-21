package org.ioopm.calculator.ast.visitor;

import org.ioopm.calculator.ast.*;

public abstract class AstChecker implements Visitor<Boolean> {
    boolean check(SymbolicExpression expr) {
        return expr.accept(this);
    }

    @Override
    public Boolean visit(Addition n) {
        n.getLhs().accept(this);
        n.getRhs().accept(this);
        return false;
    }

    @Override
    public Boolean visit(Assignment n) {
        n.getLhs().accept(this);
        n.getRhs().accept(this);
        return false;
    }

    @Override
    public Boolean visit(Clear n) {
        return false;
    }

    @Override
    public Boolean visit(Constant n) {
        return false;
    }

    @Override
    public Boolean visit(Cos n) {
        n.getRhs().accept(this);
        return false;
    }

    @Override
    public Boolean visit(Division n) {
        n.getLhs().accept(this);
        n.getRhs().accept(this);
        return false;
    }

    @Override
    public Boolean visit(Exp n) {
        n.getRhs().accept(this);
        return false;
    }

    @Override
    public Boolean visit(Log n) {
        n.getRhs().accept(this);
        return false;
    }

    @Override
    public Boolean visit(Multiplication n) {
        n.getLhs().accept(this);
        n.getRhs().accept(this);
        return false;
    }

    @Override
    public Boolean visit(Negation n) {
        n.getRhs().accept(this);
        return false;
    }

    @Override
    public Boolean visit(Quit n) {
        return false;
    }

    @Override
    public Boolean visit(Sin n) {
        n.getRhs().accept(this);
        return false;
    }

    @Override
    public Boolean visit(Subtraction n) {
        n.getLhs().accept(this);
        n.getRhs().accept(this);
        return false;
    }

    @Override
    public Boolean visit(Variable n) {
        return false;
    }

    @Override
    public Boolean visit(Vars n) {
        return false;
    }
}
