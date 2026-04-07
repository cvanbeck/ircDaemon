package com.github.cvanb002.irc;

import com.github.cvanb002.model.Message;


public class Parser {
    // TODO: THis implementation is a mess, defo needs a rework
    int startIndex;
    int currentIndex;

    public Parser() {
    }

    public Message parse(String input) {
        boolean inputTooLong = input.length() > IRC.Constants.MAXLENGTH;
        boolean noCRLF = !input.endsWith("\r\n");

        if (inputTooLong) {
            return new Message(IRC.Numeric.ERR_INPUT_TOO_LONG);
        }
        if (noCRLF) {
            return new Message(IRC.Numeric.ERR_UNKNOWNERROR);
        }
        return scanMessage(input);
    }

    private Message scanMessage(String input) {
        String token;
        Message message = new Message();

        startIndex = 0;
        currentIndex = 0;

        while (!finished(input)) {
            char currentChar = input.charAt(currentIndex);

            if (currentChar == IRC.Constants.SEPERATOR || endOfLine(input)) {
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

    private boolean endOfLine(String input) {
        return input.substring(currentIndex).equals("\r\n");
    }

    private boolean finished(String input) {
        return currentIndex >= input.length();
    }
}