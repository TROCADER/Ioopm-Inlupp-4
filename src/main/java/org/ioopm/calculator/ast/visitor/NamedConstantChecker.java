package org.ioopm.calculator.ast.visitor;

import org.ioopm.calculator.ast.Assignment;
import org.ioopm.calculator.ast.Variable;

import java.util.ArrayList;

public class NamedConstantChecker extends AstChecker {
    final ArrayList<Assignment> wrongAssignments = new ArrayList<>();

    @Override
    public Boolean visit(Assignment n) {
        super.visit(n);
        if (!(n.getRhs() instanceof Variable)) {
            wrongAssignments.add(n);
            return false;
        }

        return true;
    }

    public ArrayList<Assignment> getWrongAssignments() {
        return wrongAssignments;
    }
}
