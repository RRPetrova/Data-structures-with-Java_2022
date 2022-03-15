package implementations;

import interfaces.Solvable;

import java.util.ArrayDeque;

public class BalancedParentheses implements Solvable {
    private String parentheses;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {
        ArrayDeque<String> leftSide = new ArrayDeque<>();
        boolean isEqual = false;

        if (this.parentheses.length() % 2 != 0) {
            return false;
        }

        for (int i = 0; i < this.parentheses.length(); i++) {
            if (this.parentheses.charAt(i) == '(' || this.parentheses.charAt(i) == '['
                    || this.parentheses.charAt(i) == '{') {
                leftSide.push(String.valueOf(this.parentheses.charAt(i)));
            } else {
                String currentRight = String.valueOf(this.parentheses.charAt(i));

                if (leftSide.isEmpty()) {
                    isEqual = false;
                    break;
                }

                if (leftSide.peek().equals("{") && currentRight.equals("}")) {
                    isEqual = true;
                    leftSide.pop();
                } else if (leftSide.peek().equals("(") && currentRight.equals(")")) {
                    isEqual = true;
                    leftSide.pop();
                } else if (leftSide.peek().equals("[") && currentRight.equals("]")) {
                    isEqual = true;
                    leftSide.pop();
                } else {
                    break;
                }
            }
        }

        if (isEqual == true) {
        return true;
        } else {
            return false;
        }
    }
}

