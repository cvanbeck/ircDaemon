package com.github.cvanb002.irc;

import com.github.cvanb002.model.Message;


public class Scanner {
    // TODO: THis implementation is a mess, defo needs a rework
    int start;
    int current;
    String input = "";

    public Scanner() {
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
        String token = "";
        Message message = new Message();
        start = 0;
        current = 0;

        while (!finished()) {
            char c = input.charAt(current);
            // Checks for extended parameter
            if (c == ':' && !(message.getCommand().isEmpty())) {
                token = input.substring(start, input.length() - 4).strip();
                message.addParameter(token);
                return message;
            }

            if (c == IRC.Constants.SEPERATOR || endOfLine()) {
                token = input.substring(start, current).strip();
            // If params begin with : then the rest of the message is a singular param.
                if(token.charAt(0) == ':' && start == 0){
                    message.addSource(token.substring(1));
                }
                else if(message.getCommand().isEmpty()){
                    message.addCommand(token);
                }
                else {
                    message.addParameter(token);
                }
                start = current;
            }
            current++;
        }
        return message;
    }

    private boolean endOfLine() {
        return input.substring(current).equals("\\r\\n");
    }

    private boolean finished() {
        return current >= input.length();
    }

    // TODO: Most of these functions should be private, so need a rewrite.
    private boolean containsCRLF() {
        return input.endsWith("\\r\\n");
    }

    private boolean inputTooLong() {
        // Maximum message length for IRC is 512 bytes
        return input.length() > IRC.Constants.MAXLENGTH;
    }
}