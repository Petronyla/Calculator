import java.util.*;

import static java.lang.Integer.*;

public class Calculator {

    private static Deque<String> stackOp = new ArrayDeque<>();
    private static Deque<Integer> stackNum = new ArrayDeque<>();
    private static Map<String, Integer> operators = new HashMap<>();

    public static void main(String[] args) {
        String input = "5 * (   4 +8) + 2";
        input = input.replaceAll(" ", "");

        operators.put("+", 1);
        operators.put("-", 1);
        operators.put("*", 2);
        operators.put("/", 2);
        operators.put("^", 3);
        operators.put("(", 4);
        operators.put(")", 4);

        List<String> list = new ArrayList<>(Arrays.asList(input.split("")));

        do {
            for (int i = 0; list.size() > i; i++) {
                if (operators.containsKey(list.get(i))) {
                    stackOp.push(list.get(i));
                } else {
                    try {
                        stackNum.push(parseInt(list.get(i)));
                    } catch (Exception e) {
                        System.out.println("Unexpected character.");
                    }
                }
                if (stackOp.size() >= 1 && stackNum.size() >= 2) {
                    if (stackOp.size() >= 2) {
                        String lastOp = stackOp.pollFirst();
                        String secondLastOp = stackOp.pollFirst();
                        if (lastOp.equals(")")) {
                            do {
                                stackNum.push(calculate(secondLastOp));
                            } while (!stackOp.getFirst().equals("("));
                            stackOp.pollFirst();
                        } else if (operators.get(secondLastOp) >= operators.get(lastOp) && !secondLastOp.equals("(")) {
                            stackNum.push(calculate(secondLastOp));
                            stackOp.push(lastOp);
                        } else {
                            stackOp.push(secondLastOp);
                            stackOp.push(lastOp);
                        }
                    }
                    if (i == list.size() - 1) {
                        while (stackOp.size() > 0)
                            stackNum.push(calculate(stackOp.pollFirst()));
                    }
                }
            }
        } while (!(stackOp.size() == 0));

        System.out.println(stackNum.poll());

    }

    public static int calculate(String op) {
        int secondNumber = stackNum.pollFirst();
        int firstNumber = stackNum.pollFirst();
        int result = 0;
        switch (op) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                result = firstNumber / secondNumber;
                break;
            case "^":
                result = (int) Math.pow((double) firstNumber, (double) secondNumber);
        }
        return result;
    }

}
