package com.github.cvanb002.IrcTest;


import static org.junit.jupiter.api.Assertions.*;
import com.github.cvanb002.irc.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;


public class ParserTest {

    @Test
    public void scannerChecksCRLF(){
        String containsCRLF = ":alice!alice@host PRIVMSG #chatroom :Hello!\\r\\n";
        String notContainsCRLF = ":alice!alice@host PRIVMSG #chatroom :Hello!";


        Scanner scanner = new Scanner(containsCRLF);
        assertTrue(scanner.checkCRLF());

        scanner = new Scanner(notContainsCRLF);
        assertFalse(scanner.checkCRLF());

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
        assertTrue(scanner.inputTooLong());

        scanner = new Scanner(string.repeat(513));
        assertFalse(scanner.inputTooLong());
    }

    @Test
    public void tokeniserSplitsCorrectly(){
        List<String> tokenised = new ArrayList<String>();

        String input = ":alice!alice@host PRIVMSG #chatroom :Hello! How are you";
        List<String> correctOutput = new ArrayList<String>()  ;
        correctOutput.add(":alice!alice@host");
        correctOutput.add("PRIVMSG");
        correctOutput.add("#chatroom");
        correctOutput.add(":Hello! How are you");


        Scanner scanner = new Scanner(input);
        tokenised = scanner.scanTokens();
        assertEquals(correctOutput, tokenised);


        input = "CAP REQ :sasl message-tags foo";
        correctOutput = new ArrayList<String>()  ;
        correctOutput.add("CAP");
        correctOutput.add("REQ");
        correctOutput.add(":sasl message-tags foo");
        scanner = new Scanner(input);
        tokenised = scanner.scanTokens();
        assertEquals(correctOutput, tokenised);

        input = "@id=234AB :dan!d@localhost PRIVMSG #chan :Hey what's up!";
        correctOutput = new ArrayList<String>()  ;
        correctOutput.add("@id=234AB");
        correctOutput.add(":dan!d@localhost");
        correctOutput.add("PRIVMSG");
        correctOutput.add("#chan");
        correctOutput.add(":Hey what's up!");
        scanner = new Scanner(input);
        tokenised = scanner.scanTokens();
        assertEquals(correctOutput, tokenised);




    }

}
