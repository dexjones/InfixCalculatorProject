/*
Author: Dexter Jones
Infix calculator that evaluates arithmetic expressions in infix notation. The program
supports the basic operations:
* Addition (+)
* Subtraction (-)
* Multiplication (*)
* Division (/ and %)

Additionally, program should handle operands and display final result
* Provides a sample main method that demonstrates the functionality
 */
public class Main {
    public static void main(String[] args) {
        InfixCalculator calculator = new InfixCalculator();
        String result;
        // Example 1: Valid Expression
        String expression1 = "(4+2)*3";
        calculator.setExpression(expression1);
        System.out.println("Result 1:\n" + calculator.results());

        // Example 2: Valid Expression
        String expression2 = "5+(3*7)";
        calculator.setExpression(expression2);
        System.out.println("Result 2:\n" + calculator.results());

        // Example 3: Invalid Expression
        String expression3 = "4+2*3"; // Missing parentheses
        calculator.setExpression(expression3);
        System.out.println("Result 3:\n" + calculator.results());

        String expression4 = "10*5)+3";
        calculator.setExpression(expression4);
        System.out.println("Result 4:\n" + calculator.results());
    }
}