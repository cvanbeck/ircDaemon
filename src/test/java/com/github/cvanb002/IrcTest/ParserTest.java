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
    public void scannerStripsCRLF(){
        String containsCRLF = ":alice!alice@host PRIVMSG #chatroom :Hello!\\r\\n";
        String notContainsCRLF = ":alice!alice@host PRIVMSG #chatroom :Hello!";

        Scanner scanner = new Scanner(containsCRLF);
        assertEquals(notContainsCRLF, scanner.stripCRLF());

        scanner = new Scanner(notContainsCRLF);
        assertThrows(InputMismatchException.class, scanner::stripCRLF);
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
            assertEquals(first.get(i).getType(), second.get(i).getType());
            assertEquals(first.get(i).getToken(), second.get(i).getToken());
        }
    }


    @Test
    public void tokeniserSplitsCorrectly(){
        List<Token> tokenised = new ArrayList<>();

        String input = ":alice!alice@host PRIVMSG #chatroom :Hello! How are you";
        List<Token> correctOutput = new ArrayList<>()  ;
        correctOutput.add(new Token(":alice!alice@host", IRC.Type.SOURCE));
        correctOutput.add(new Token("PRIVMSG", IRC.Type.COMMAND));
        correctOutput.add(new Token("#chatroom", IRC.Type.PARAMETER));
        correctOutput.add(new Token(":Hello! How are you", IRC.Type.PARAMETER));
        Scanner scanner = new Scanner(input);
        tokenised = scanner.scanTokens();
        checkEqual(correctOutput, tokenised);

        input = "CAP REQ :sasl message-tags foo";
        correctOutput = new ArrayList<>()  ;
        correctOutput.add(new Token("CAP", IRC.Type.COMMAND));
        correctOutput.add(new Token("REQ", IRC.Type.PARAMETER));
        correctOutput.add(new Token(":sasl message-tags foo", IRC.Type.PARAMETER));
        scanner = new Scanner(input);
        tokenised = scanner.scanTokens();
        checkEqual(correctOutput, tokenised);

        input = "@id=234AB :dan!d@localhost PRIVMSG #chan :Hey what's up!";
        correctOutput = new ArrayList<>()  ;
        correctOutput.add(new Token("@id=234AB", IRC.Type.TAG));
        correctOutput.add(new Token(":dan!d@localhost", IRC.Type.SOURCE));
        correctOutput.add(new Token("PRIVMSG", IRC.Type.COMMAND));
        correctOutput.add(new Token("#chan", IRC.Type.PARAMETER));
        correctOutput.add(new Token(":Hey what's up!", IRC.Type.PARAMETER));
        scanner = new Scanner(input);
        tokenised = scanner.scanTokens();
        checkEqual(correctOutput, tokenised);
    }
}
