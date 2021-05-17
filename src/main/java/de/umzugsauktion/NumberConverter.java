package de.umzugsauktion;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberConverter {

    public static final String UMZUGS = "Umzugs";
    public static final String AUKTION = "Auktion";
    public static final String UMZUGS_AUKTION = "UmzugsAuktion";
    public static final String REGEX = "^0*(?:[1-9][0-9]?|100)$";
    public static final String INVALID_NUMBER = "Invalid number! Please try again (1 to 100) :: ";
    public static final String DIFFERENT_NUMBER = "Invalid number! Please enter different number (1 to 100) :: ";

    public List<Object> start() {
        var scanner = new Scanner(System.in);
        System.out.print("\nPlease enter number X (1 to 100) :: ");
        var num1 = Integer.parseInt(getInput1(scanner));
        System.out.print("\nPlease enter number Y (1 to 100) :: ");
        var num2 = Integer.parseInt(getInput2(scanner, num1));
        return IntStream.rangeClosed(1, 100)
                .mapToObj(i -> i % num1 == 0
                        ? (i % num2 == 0 ? UMZUGS_AUKTION : UMZUGS)
                        : (i % num2 == 0 ? AUKTION : i))
                .collect(Collectors.toList());
    }

    public String getInput1(Scanner scanner) throws NumberFormatException {
        String number1;
        while (true) {
            number1 = scanner.nextLine().trim();
            if (number1.matches(REGEX))
                break;
            else
                System.out.print(INVALID_NUMBER);
        }
        return number1;
    }

    public String getInput2(Scanner scanner, Integer number1) throws NumberFormatException {
        String number2;
        while (true) {
            number2 = scanner.nextLine().trim();
            if (number2.matches(REGEX))
                if (Integer.parseInt(number2) == number1)
                    System.out.print(DIFFERENT_NUMBER);
                else
                    break;
            else
                System.out.print(INVALID_NUMBER);
        }
        return number2;
    }
}
