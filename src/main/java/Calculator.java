import java.util.*;

import static java.lang.Integer.*;

public class Calculator {

    private static Deque<String> stackOp = new ArrayDeque<>();
    private static Deque<Integer> stackNum = new ArrayDeque<>();
    private static Map<String, Integer> operators = new HashMap<>();

    public static void main(String[] args) {
        String input = "5 + 2 * 3 ^ 2";
        input = input.replaceAll(" ", "");

        operators.put("+", 1);
        operators.put("-", 1);
        operators.put("*", 2);
        operators.put("/", 2);
        operators.put("^", 3);
        operators.put("(", 4);
        operators.put(")", 5);

        List<String> stringList = new ArrayList<>(Arrays.asList(input.split("")));

        do {
            for (int i = 0; stringList.size() > i; i++) {
                if (operators.containsKey(stringList.get(i))) {
                    stackOp.push(stringList.get(i));
                } else {
                    try {
                        stackNum.push(parseInt(stringList.get(i)));
                    } catch (Exception e) {
                        System.out.println("Unexpected character.");
                    }
                }
                if (stackOp.size() >= 1 && stackNum.size() >= 2) {
                    if (stackOp.size() >= 2) {
                        String lastOp = stackOp.pollFirst();
                        String secondLastOp = stackOp.pollFirst();
                        if (operators.get(secondLastOp) >= operators.get(lastOp)) {
                            stackNum.push(calculate(secondLastOp));
                        } else {
                            stackOp.push(secondLastOp);
                        }
                        stackOp.push(lastOp);
                    }
                    if (i == stringList.size() - 1) {
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
