package com.github.cvanb002.IrcTest;


import static org.junit.jupiter.api.Assertions.*;
import com.github.cvanb002.irc.*;

import com.github.cvanb002.model.Message;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class ParserTest {
    @Test
    public void tokeniseCommand(){
        Scanner scanner = new Scanner("CAP\\r\\n");
        Message message = scanner.scanMessage();

        String expected = "CAP";

        assertEquals(expected, message.getCommand());
    }

    @Test
    public void tokeniseParameter(){
        Scanner scanner = new Scanner("CAP REQ\\r\\n");
        Message message = scanner.scanMessage();

        String expCommand = "CAP";
        List<String> expParameters = new ArrayList<>();
        expParameters.add("REQ");

        assertEquals(expCommand, message.getCommand());
        assertEquals(expParameters, message.getParameters());
    }

    @Test
    public void tokeniseMultipleParameter(){
        Scanner scanner = new Scanner("CAP REQ ANOTHER PARAM\\r\\n");
        Message message = scanner.scanMessage();

        List<String> expected = new ArrayList<>();
        expected.add("REQ");
        expected.add("ANOTHER");
        expected.add("PARAM");

        assertEquals("CAP", message.getCommand());
        assertEquals(expected, message.getParameters());
    }

    @Test
    public void tokeniseExtParameter(){
        Scanner scanner = new Scanner("CAP :sasl message-tags foo\\r\\n");
        Message message = scanner.scanMessage();

        List<String> expected = new ArrayList<>();
        expected.add(":sasl message-tags foo");

        assertEquals("CAP", message.getCommand());
        assertEquals(expected, message.getParameters());
    }

    @Test
    public void tokeniseParamAndExtParameter(){
        Scanner scanner = new Scanner("CAP REQ ANOTHER :sasl message-tags foo\\r\\n");
        Message message = scanner.scanMessage();

        List<String> expected = new ArrayList<>();
        expected.add("REQ");
        expected.add("ANOTHER");
        expected.add(":sasl message-tags foo");

        assertEquals("CAP", message.getCommand());
        assertEquals(expected, message.getParameters());
    }

    @Test
    public void tokeniseSource(){
        Scanner scanner = new Scanner(":alice!alice@host PRIVMSG #chatroom\\r\\n");
        Message message = scanner.scanMessage();

        List<String> expected = new ArrayList<>();
        expected.add("#chatroom");

        assertEquals("alice!alice@host", message.getSource());
        assertEquals("PRIVMSG", message.getCommand());
        assertEquals(expected, message.getParameters());
    }

    @Test
    public void tokeniseSourceAndExtParameter(){
        Scanner scanner = new Scanner(":alice!alice@host PRIVMSG #chatroom :Hello! How are you\\r\\n");
        Message message = scanner.scanMessage();

        List<String> expected = new ArrayList<>();
        expected.add("#chatroom");
        expected.add(":Hello! How are you");

        assertEquals("alice!alice@host", message.getSource());
        assertEquals("PRIVMSG", message.getCommand());
        assertEquals(expected, message.getParameters());
    }

    @Test
    public void canHandleIncorrectSequence() {
        // This wouldn't work as the command doesn't exist, buts that's for the parsers to figure out not the scanner
        Scanner scanner = new Scanner(":test!test@host :alice!alice@host PRIVMSG TEST\\r\\n");
        Message message = scanner.scanMessage();

        List<String> expected = new ArrayList<>();
        expected.add("PRIVMSG");
        expected.add("TEST");

        assertEquals("test!test@host", message.getSource());
        assertEquals(":alice!alice@host", message.getCommand());
        assertEquals(expected, message.getParameters());
    }

    @Test
    public void parseErrorCreatesNumerics(){
        String input = "1";
        Scanner scanner = new Scanner();
        Message message = scanner.parse(input.repeat(600) + "\\r\\n");

        assertEquals(417, message.getNumeric().getCode());

        input = "1";
        scanner = new Scanner(input);
        message = scanner.parse();
        assertEquals(400, message.getNumeric().getCode());
    }
}
