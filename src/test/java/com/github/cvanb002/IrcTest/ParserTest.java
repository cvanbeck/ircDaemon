package com.github.cvanb002.IrcTest;


import static org.junit.jupiter.api.Assertions.*;
import com.github.cvanb002.irc.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;


public class ParserTest {

/* TODO: These tests only work because the methods they're calling are public, realistically this is hacky and they should be
    private. Therefore I need to rewrite half of them */

    @Test
    public void scannerChecksCRLF(){
        String containsCRLF = ":alice!alice@host PRIVMSG #chatroom :Hello!\\r\\n";
        String notContainsCRLF = ":alice!alice@host PRIVMSG #chatroom :Hello!";

        Scanner scanner = new Scanner(containsCRLF);
        assertTrue(scanner.containsCRLF());

        scanner = new Scanner(notContainsCRLF);
        assertFalse(scanner.containsCRLF());
    }


    @Test
    public void ignoresMessagesGreaterThan512Chars(){
        String string = "a";
        Scanner scanner = new Scanner(string);
        assertFalse(scanner.inputTooLong());

        scanner = new Scanner(string.repeat(513));
        assertTrue(scanner.inputTooLong());
    }


    public void checkEqual(List<Token> first, List<Token> second){
        for(int i = 0; i < first.size(); i++){
            System.out.println(first.get(i).getToken() + " : " + second.get(i).getToken());
            System.out.println(first.get(i).getType() + " : " + second.get(i).getType());
            assertEquals(first.get(i).getType(), second.get(i).getType());
            assertEquals(first.get(i).getToken(), second.get(i).getToken());
        }
            System.out.println(" ");
    }


    @Test
    public void tokeniseCommand(){
        List<Token> tokens;
        Scanner scanner = new Scanner("CAP\\r\\n");
        tokens = scanner.scanTokens();

        List<Token> expected = new ArrayList<>();
        expected.add(new Token("CAP", IRC.Type.COMMAND));

        checkEqual(expected, tokens);
    }

    @Test
    public void tokeniseParameter(){
        List<Token> tokens;
        Scanner scanner = new Scanner("CAP REQ\\r\\n");
        tokens = scanner.scanTokens();

        List<Token> expected = new ArrayList<>();
        expected.add(new Token("CAP", IRC.Type.COMMAND));
        expected.add(new Token("REQ", IRC.Type.PARAMETER));

        checkEqual(expected, tokens);
    }

    @Test
    public void tokeniseMultipleParameter(){
        List<Token> tokens;
        Scanner scanner = new Scanner("CAP REQ ANOTHER PARAM\\r\\n");
        tokens = scanner.scanTokens();

        List<Token> expected = new ArrayList<>();
        expected.add(new Token("CAP", IRC.Type.COMMAND));
        expected.add(new Token("REQ", IRC.Type.PARAMETER));
        expected.add(new Token("ANOTHER", IRC.Type.PARAMETER));
        expected.add(new Token("PARAM", IRC.Type.PARAMETER));

        checkEqual(expected, tokens);
    }

    @Test
    public void tokeniseExtParameter(){
        List<Token> tokens;
        Scanner scanner = new Scanner("CAP :sasl message-tags foo\\r\\n");
        tokens = scanner.scanTokens();

        List<Token> expected = new ArrayList<>();
        expected.add(new Token("CAP", IRC.Type.COMMAND));
        expected.add(new Token(":sasl message-tags foo", IRC.Type.PARAMETER));

        checkEqual(expected, tokens);
    }

    @Test
    public void tokeniseParamAndExtParameter(){
        List<Token> tokens;
        Scanner scanner = new Scanner("CAP REQ ANOTHER :sasl message-tags foo\\r\\n");
        tokens = scanner.scanTokens();

        List<Token> expected = new ArrayList<>();
        expected.add(new Token("CAP", IRC.Type.COMMAND));
        expected.add(new Token("REQ", IRC.Type.PARAMETER));
        expected.add(new Token("ANOTHER", IRC.Type.PARAMETER));
        expected.add(new Token(":sasl message-tags foo", IRC.Type.PARAMETER));

        checkEqual(expected, tokens);
    }

    @Test
    public void tokeniseSource(){
        List<Token> tokens;
        Scanner scanner = new Scanner(":alice!alice@host PRIVMSG #chatroom\\r\\n");
        tokens = scanner.scanTokens();

        List<Token> expected = new ArrayList<>();
        expected.add(new Token(":alice!alice@host", IRC.Type.SOURCE));
        expected.add(new Token("PRIVMSG", IRC.Type.COMMAND));
        expected.add(new Token("#chatroom", IRC.Type.PARAMETER));

        checkEqual(expected, tokens);
    }

    @Test
    public void tokeniseSourceAndExtParameter(){
        List<Token> tokens;
        Scanner scanner = new Scanner(":alice!alice@host PRIVMSG #chatroom :Hello! How are you\\r\\n");
        tokens = scanner.scanTokens();

        List<Token> expected = new ArrayList<>();
        expected.add(new Token(":alice!alice@host", IRC.Type.SOURCE));
        expected.add(new Token("PRIVMSG", IRC.Type.COMMAND));
        expected.add(new Token("#chatroom", IRC.Type.PARAMETER));
        expected.add(new Token(":Hello! How are you", IRC.Type.PARAMETER));

        checkEqual(expected, tokens);
    }

    @Test
    public void tokeniseTag(){
        List<Token> tokens;
        Scanner scanner = new Scanner("@id=234AB PRIVMSG\\r\\n");
        tokens = scanner.scanTokens();

        List<Token> expected = new ArrayList<>();
        expected.add(new Token("@id=234AB", IRC.Type.TAG));
        expected.add(new Token("PRIVMSG", IRC.Type.COMMAND));

        checkEqual(expected, tokens);
    }

    @Test
    public void canHandleIncorrectSequence() {
        List<Token> tokens;
        Scanner scanner = new Scanner(":alice!alice@host @id=234AB @id=234AB @id=234AB\\r\\n");
        tokens = scanner.scanTokens();

        List<Token> expected = new ArrayList<>();
        // This wouldn't work as the command doesn't exist, buts that's for the parsers to figure out not the scanner
        expected.add(new Token(":alice!alice@host", IRC.Type.SOURCE));
        expected.add(new Token("@id=234AB", IRC.Type.COMMAND));
        expected.add(new Token("@id=234AB", IRC.Type.PARAMETER));
        expected.add(new Token("@id=234AB", IRC.Type.PARAMETER));

        checkEqual(expected, tokens);
    }
}
