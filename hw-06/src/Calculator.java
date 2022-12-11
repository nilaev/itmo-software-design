import token.Token;
import tokenizer.ExpressionTokenizer;
import visitor.*;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        System.out.println(
                "Вы запустили калькулятор обратной польской записи\n" +
                "Введите 'calc <выражение>', чтобы поссчитать выражение в ОПЗ,\n" +
                "Введите 'exit', чтобы завершить работу программы"
        );
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            switch (scanner.next().toLowerCase()) {
                case "calc" -> {
                    try {
                        String expression = scanner.nextLine();
                        InputStream inputStream = new ByteArrayInputStream(expression.getBytes());

                        // Токены
                        List<Token> tokens = new ExpressionTokenizer().tokenize(inputStream);
                        System.out.print("Токены: ");
                        new PrintVisitor(System.out).printExpr(tokens);
                        System.out.println();

                        // ОПЗ
                        List<Token> reversePolishNotation = new ParserVisitor().parse(tokens);
                        System.out.print("ОПЗ:    ");
                        new PrintVisitor(System.out).printExpr(reversePolishNotation);
                        System.out.println();

                        // Результат
                        System.out.println("Результат: " + new CalcVisitor().calculate(reversePolishNotation));
                    } catch (RuntimeException | IOException e) {
                        e.printStackTrace();
                    }
                }
                case "exit" -> {
                    System.out.println("До скорых встреч!");
                    System.exit(0);
                }
                default -> System.out.println("Неизвестная команда");
            }
        }
    }

}
