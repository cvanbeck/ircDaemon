package com.github.cvanb002.irc;

import com.github.cvanb002.model.Message;


public class Parser {
    // TODO: THis implementation is a mess, defo needs a rework
    int startIndex;
    int currentIndex;
    String input = "";

    public Parser() {
    }

    public Message parse(String input) {
        this.input = input;
        /* TODO: Need to rewrite this, should return a Message with the correct numeric */
        if (inputTooLong()) {
            return new Message(IRC.Numeric.ERR_INPUT_TOO_LONG);
        }
        if (!containsCRLF()) {
            return new Message(IRC.Numeric.ERR_UNKNOWNERROR);
        }
        return scanMessage(input);
    }

    private Message scanMessage(String input) {
        String token;
        Message message = new Message();

        startIndex = 0;
        currentIndex = 0;

        while (!finished()) {
            char currentChar = input.charAt(currentIndex);

            if (currentChar == IRC.Constants.SEPERATOR || endOfLine()) {
                token = input.substring(startIndex, currentIndex).strip();

                if(token.charAt(0) == ':' && startIndex == 0){
                    message.addSource(token.substring(1));
                }
                else if(message.getCommand().isEmpty()){
                    message.addCommand(token);
                }
                else if (token.charAt(0) == ':') {
                    // Extended params
                    token = input.substring(startIndex).strip();
                    message.addParameter(token);
                    return message; // If an extended parameter is present then you don't need to continue looping
                }
                else {
                    // Regular params
                    message.addParameter(token);
                }
                startIndex = currentIndex;
            }
            currentIndex++;
        }
        return message;
    }

    private boolean endOfLine() {
        return input.substring(currentIndex).equals("\r\n");
    }

    private boolean finished() {
        return currentIndex >= input.length();
    }

    private boolean containsCRLF() {
        return input.endsWith("\r\n");
    }

    private boolean inputTooLong() {
        // Maximum message length for IRC is 512 bytes
        return input.length() > IRC.Constants.MAXLENGTH;
    }
}