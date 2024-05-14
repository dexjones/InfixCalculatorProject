/*
Author: Dexter Jones

* Class named InfixCalculator
* Method evaluateInfix that takes an infix expression as input and returns result
* Program should handle both single-digit and multi-digit operands
* Program should handle valid infix expressions
* Display an error message for invalid expressions
 */
import java.util.ArrayList;
import java.util.List;
public class InfixCalculator {
    private String exp;
    private boolean OK;
    private int errSpot;
    private String errMessage;
    private int solution;
    private List<String> tokens;
    private LinkedStack<Integer> numStack;
    private LinkedStack<Character> charStack;
    public InfixCalculator() {
        this.exp = "";
        this.tokens = new ArrayList<>();
        this.numStack = new LinkedStack<>();
        this.charStack = new LinkedStack<>();
    }
    public InfixCalculator(String exp) {
        this.exp = exp;
        this.tokens = new ArrayList<>();
        this.numStack = new LinkedStack<>();
        this.charStack = new LinkedStack<>();
    }
    public void setExpression(String exp) {
        this.exp = exp;
        evaluateInfix(exp);
    }
    public String results() {
        StringBuilder space = new StringBuilder();
        if (!OK) {
            for (int i = 0; i < errSpot; i++) {
                space.append(" ");
            }
            return exp + "\n" + space + errMessage;
        }

        else {
            return exp + " = " + solution;
        }
    }
    private void split() {
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        LinkedStack<Character> cStack = new LinkedStack<>();
        OK = true;
        int state = 0;

        for (int i = 0; i < exp.length() && OK; i++) {
            char currentChar = exp.charAt(i);
            if (checkChar(currentChar)) {
                if (state == 0) {
                    if (!Character.isWhitespace(currentChar)) {
                        if (Character.isDigit(currentChar)) {
                            s1.setLength(0); // Erase s1
                            while (i < exp.length() && Character.isDigit(exp.charAt(i))) {
                                s1.append(exp.charAt(i));
                                i++;
                            }
                            state = 1;
                            tokens.add(s1.toString());
                            i--;
                        }
                        else if (isOp(currentChar)) {
                            OK = false;
                            errSpot = i;
                            errMessage = "^ Operand or ( expected";
                            solution = 0;
                            return;
                        }
                        else if (currentChar == '(') {
                            cStack.push(currentChar);
                        }
                        else if (currentChar == ')') {
                            if (cStack.isEmpty()) {
                                OK = false;
                                errSpot = i;
                                errMessage = "^ unmatched )";
                                solution = 0;
                                return;
                            }
                            cStack.pop();
                        }
                    }
                }
                else if (state == 1) {
                    if (!Character.isWhitespace(currentChar)) {
                        if (Character.isDigit(currentChar)) {
                            OK = false;
                            errSpot = i;
                            errMessage = "^ Operator or ) expected";
                            solution = 0;
                            return;
                        }
                        else if (isOp(currentChar)) {
                            s2.setLength(0); // Erase s2
                            s2.append(currentChar);
                            tokens.add(s2.toString());
                            state = 0;
                        }
                        else if (currentChar == '(') {
                            OK = false;
                            errSpot = i;
                            errMessage = "^ Operator or ) expected";
                            solution = 0;
                            return;
                        }
                        else if (currentChar == ')') {
                            if (cStack.isEmpty()) {
                                OK = false;
                                errSpot = i;
                                errMessage = "^ unmatched )";
                                solution = 0;
                                return;
                            }
                            cStack.pop();
                        }
                    }
                }
            }
            else {
                OK = false;
                errSpot = i;
                errMessage = "^ invalid character";
                solution = 0;
                return;
            }
        }
        if (!cStack.isEmpty()) {
            cStack.clear();
            OK = false;
            errSpot = exp.length();
            errMessage = "^ not enough )'s";
            solution = 0;
            return;
        }
        if (state == 0) {
            OK = false;
            errSpot = exp.length();
            errMessage = "^ end of expression and Operand or ( expected";
            solution = 0;
        }
    }
    private boolean checkChar(char c) { // returns true if c is a valid character or space
        if (Character.isDigit(c) || c == ' ' || isOp(c) || c == '(' || c == ')') {
            return true;
        }
        else {
            return false;
        }
    }
    private boolean isOp(char c) { // checks to see if the char is an operator
        if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%') {
            return true;
        }
        else {
            return false;
        }
    }
    private int opVal(char op) {
        // Placeholder method for operator precedence
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/', '%' -> 2;
            case '^' -> 3;
            default -> -1;
        };
    }
    private boolean lower(char op1, char op2) {
        return opVal(op1) < opVal(op2);
    }
    private void processTheOperator() {
        int nr = numStack.pop();
        char o1 = charStack.pop();
        int nl = numStack.pop();
        int r;

        switch (o1) {
            case '+' -> r = nl + nr;
            case '-' -> r = nl - nr;
            case '*' -> r = nl * nr;
            case '/' -> r = nl / nr;
            case '%' -> r = nl % nr;
            case '^' -> r = (int) Math.pow(nl, nr);
            default -> throw new IllegalStateException("Unexpected value: " + o1);
        }

        numStack.push(r);
    }
    public void evaluateInfix(String infixExpression) {
        int num;
        char first;
        String t;

        split();

        if (!OK) {
            tokens.clear();
            return;
        }

        for  (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            first = token.charAt(0);

            if (isOp(first)) {
                while (!charStack.isEmpty() && charStack.peek() != '(' && !lower(charStack.peek(), first)) {
                    processTheOperator();
                }
                charStack.push(first);
            } else if (first == '(') {
                charStack.push(first);
            } else if (first == ')') {
                while (charStack.peek() != '(') {
                    processTheOperator();
                }
                charStack.pop();
            } else {
                num = Integer.parseInt(token);
                numStack.push(num);
            }
        }

        while (!charStack.isEmpty()) {
            processTheOperator();
        }
        solution = numStack.peek();
        tokens.clear();
        numStack.clear();
    }
}
