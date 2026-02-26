package com.github.cvanb002.irc;

public class Parser {
    public static void run(String input){
        Scanner scanner = new Scanner(input);
        List<Token> tokens = scanner.scanTokens();

        for (Token token : tokens){
            System.out.println(token);
        }

    }
}