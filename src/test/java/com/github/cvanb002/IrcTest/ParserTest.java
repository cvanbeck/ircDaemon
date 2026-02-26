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

//    @Test
//    public void ignoresMessagesGreaterThan512Chars(){
//
//    }
//
//    @Test
//    public void tokeniserSplitsCorrectly(){
//        String input = ":alice!alice@host PRIVMSG #chatroom :Hello!\\r\\n";
//        List<String> tokenised = new ArrayList<String>();
//        List<String> correctOutput = new ArrayList<String>()  ;
//        correctOutput.add(":alice!alice@host");
//        correctOutput.add("PRIVMSG");
//        correctOutput.add("#chatroom");
//        correctOutput.add(":Hello!");
//
//
//        Scanner scanner = new Scanner(input);
//        tokenised = scanner.scanTokens();
//
//        assertEquals(tokenised, correctOutput);
//    }

}
