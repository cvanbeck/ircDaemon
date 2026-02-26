package com.github.cvanb002.IrcTest;


import static org.junit.jupiter.api.Assertions.*;
import com.github.cvanb002.irc.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class ParserTest {

    @Test
    public void scannerChecksCRLF(){
        String containsCRLF = ":alice!alice@host PRIVMSG #chatroom :Hello!\\r\\n";
        String notContainsCRLF = ":alice!alice@host PRIVMSG #chatroom :Hello!";


        Scanner scanner = new Scanner(containsCRLF);
        assertTrue(Scanner.checkCRLF());

        scanner = new Scanner(notContainsCRLF);
        assertFalse(Scanner.checkCRLF(notContainsCRLF));

    }


//    @Test
//    public void scannerStripsCRLF(){
//
//    }
//
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
