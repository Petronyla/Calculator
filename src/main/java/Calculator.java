import java.util.*;

import static java.lang.Integer.*;

public class Calculator {

    private static Deque<String> stackOp = new ArrayDeque<>();
    private static Deque<Integer> stackNum = new ArrayDeque<>();
    private static Map<String, Integer> operators = new HashMap<>();

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter your expression:");
            String input = sc.nextLine();
            input = input.replaceAll(" ", "");

            operators.put("+", 1);
            operators.put("-", 1);
            operators.put("*", 2);
            operators.put("/", 2);
            operators.put("^", 3);
            operators.put("(", 4);
            operators.put(")", 4);

            List<String> list = new ArrayList<>(Arrays.asList(input.split("")));
            StringBuilder sb = new StringBuilder();

            for (int i = 0; list.size() > i; i++) {
//                if ((list.get(0).equals("-")) || i > 0 && list.get(i).equals("-") && operators.containsKey(list.get(i - 1))) {
//                    StringBuilder sb = new StringBuilder();
//                    sb.append("-");
//                }
                if (operators.containsKey(list.get(i))) {
                    stackOp.push(list.get(i));
                } else {

                    //treats a situation where there is a multi-digit number in the expression
                    sb.append(input.charAt(i));
                    for (int j = i + 1; j < list.size(); j++) {
                        char ch = input.charAt(j);
                        if (Character.isDigit(ch)) {
                            sb.append(ch);
                            i++;
                        } else {
                            break;
                        }
                    }

                    try {
                        stackNum.push(Integer.parseInt(sb.toString()));
                        sb.setLength(0);
                    } catch (Exception e) {
                        System.out.println("Unexpected character.");
                    }
                }

                if (stackOp.size() >= 1 && stackNum.size() >= 2) {
                    if (stackOp.size() >= 2) {
                        String lastOp = stackOp.pollFirst();
                        String secondLastOp = stackOp.pollFirst();

                        //calculates the expression in parentheses
                        if (lastOp.equals(")")) {
                            do {
                                stackNum.push(calculate(secondLastOp));
                            } while (!stackOp.getFirst().equals("("));
                            stackOp.pollFirst();

                            //compares the two operators and, if the first operator has the higher priority, performs the
                            // calculation
                        } else if (operators.get(secondLastOp) >= operators.get(lastOp) && !secondLastOp.equals("(")) {
                            stackNum.push(calculate(secondLastOp));
                            stackOp.push(lastOp);

                            //continues unchanged
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

            System.out.print("The result is: ");
            System.out.println(stackNum.poll());
        }
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
