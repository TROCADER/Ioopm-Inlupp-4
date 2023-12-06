package org.ioopm.calculator;

import org.ioopm.calculator.ast.*;
import org.ioopm.calculator.ast.visitor.CallDepthExceededException;
import org.ioopm.calculator.ast.visitor.EvaluationVisitor;
import org.ioopm.calculator.ast.visitor.NamedConstantChecker;
import org.ioopm.calculator.ast.visitor.ReassignmentChecker;
import org.ioopm.calculator.parser.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class Calculator {
    int fullyEvaluated = 0;
    int enteredExpressions = 0;

    PrintStream out;
    Scanner scanner;

    public Calculator() {
        this.scanner = new Scanner(System.in);
        this.out = System.out;
    }

    public Calculator(Scanner scanner, PrintStream out) {
        this.out = out;
        this.scanner = scanner;
    }

    public void runEventLoop() throws IOException {
        CalculatorParser parser = new CalculatorParser();
        EnvironmentScopes vars = new EnvironmentScopes();
        vars.pushEnvironment();
        out.println("Welcome to the Symbolic Calculator!");

        boolean running = true;
        while (running) {
            final SymbolicExpression expr;
            printSymbol('?');
            try {
                expr = parser.parse(scanner);
            } catch (SyntaxErrorException | IllegalExpressionException | CallDepthExceededException exception) {
                out.println(exception.getMessage());
                continue;
            }

            running = runEval(vars, running, expr);
        }

        printStatisics();
    }

    private void printSymbol(char symbol) {
        out.print(symbol + " ");
    }

    private boolean runEval(EnvironmentScopes vars, boolean running, final SymbolicExpression expr) {
        if (expr.isCommand()) {
            running = handleCommand(vars, running, expr);
        } else {
            enteredExpressions += 1;
            NamedConstantChecker namedConstantChecker = new NamedConstantChecker();
            if (!namedConstantChecker.check(expr)) {
                printNamedConstantError(namedConstantChecker);
                return running;
            }

            ReassignmentChecker reassignmentChecker = new ReassignmentChecker();
            if (!reassignmentChecker.check(expr)) {
                printReassignmentError(reassignmentChecker);
                return running;
            }

            SymbolicExpression evaluated = null;

            evaluated = evaluateExpr(vars, expr, evaluated);

            handleEval(vars, evaluated);
        }

        return running;
    }

    private boolean handleCommand(EnvironmentScopes vars, boolean running, final SymbolicExpression expr) {
        if (expr.equals(Clear.instance())) {
            vars.clear();
        } else if (expr.equals(Quit.instance())) {
            running = false;
        } else if (expr.equals(Vars.instance())) {
            out.println(vars);
        } else {
            out.println("Not an implemented command");
        }

        return running;
    }

    private SymbolicExpression evaluateExpr(EnvironmentScopes vars, final SymbolicExpression expr,
            SymbolicExpression evaluated) {
        try {
            evaluated = new EvaluationVisitor(vars).evaluate(expr);
        } catch (WrongArgumentNumberException | IllegalExpressionException | CallDepthExceededException e) {
            out.println(e.getMessage());
        }

        return evaluated;
    }

    private void handleEval(EnvironmentScopes vars, SymbolicExpression evaluated) {
        if (evaluated != null) {
            out.println(evaluated);

            vars.put(new Variable("ans"), evaluated);

            if (evaluated.isConstant()) {
                fullyEvaluated += 1;
            }
        }
    }

    private void printNamedConstantError(NamedConstantChecker namedConstantChecker) {
        out.println("Error: Can not reassign named constants");

        for (Assignment assignment : namedConstantChecker.getWrongAssignments()) {
            out.println("    " + assignment);
        }
    }

    private void printReassignmentError(ReassignmentChecker reassignmentChecker) {
        HashSet<Variable> reassignedVariables = reassignmentChecker.getReassignedVariables();
        out.print("Error: Can't reassign the variable");
        if (reassignedVariables.size() != 1) {
            out.print("s");
        }
        out.print(" ");
        for (Iterator<Variable> iterator = reassignedVariables.iterator(); iterator.hasNext();) {
            Variable v = iterator.next();
            out.print(v.toString());

            if (iterator.hasNext()) {
                out.print(", ");
            }
        }

        out.println(" more than once");
    }

    public static void main(String[] args) throws IOException {
        Calculator calculator = new Calculator();

        calculator.runEventLoop();
    }

    private void printStatisics() {
        out.println("Entered Expressions: " + enteredExpressions);
        out.println("Fully Evaluated: " + fullyEvaluated);
    }
}