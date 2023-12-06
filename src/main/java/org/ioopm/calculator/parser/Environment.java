package org.ioopm.calculator.parser;

import org.ioopm.calculator.ast.FunctionDeclaration;
import org.ioopm.calculator.ast.SymbolicExpression;
import org.ioopm.calculator.ast.Variable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

public class Environment extends HashMap<Variable, SymbolicExpression> {
    @Override
    public String toString() {
        StringBuilder strBld = new StringBuilder();

        strBld.append("Variables: ");
        TreeSet<Variable> vars = new TreeSet<>(this.keySet());

        for (Iterator<Variable> iter = vars.iterator(); iter.hasNext();) {
            Variable var = iter.next();

            strBld.append(var.getName());
            strBld.append(" = ");

            SymbolicExpression varContent = this.get(var);
            if (varContent instanceof FunctionDeclaration f) {
                strBld.append(f.shortToString());
            } else {
                strBld.append(varContent);
            }

            if (iter.hasNext()) {
                strBld.append(", ");
            }
        }

        return strBld.toString();
    }

    public void pushEnvironment() {

    }

    public void popEnvironment() {

    }
}