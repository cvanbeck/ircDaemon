package com.github.cvanb002.irc;

import com.github.cvanb002.model.Message;


public class Scanner {
    // TODO: THis implementation is a mess, defo needs a rework
    int start;
    int current;
    String input = "";

    public Scanner(String input) {
        this.input = input;
    }


    public Message parse() {
        /* TODO: Need to rewrite this, should return a Message with the correct numeric */
        if (inputTooLong()) {
            // Placeholder
            //return IRC.Numeric.ERR_INPUT_TOO_LONG;
            return new Message("ERR_INPUT_TOO_LONG");
        }
        if (!containsCRLF()) {
            //return IRC.Numeric.ERR_UNKNOWNERROR;
            return new Message("ERR_UNKNOWNERROR");
        }

        Message message = scanMessage();
        return scanMessage();
    }

    public Message scanMessage() {
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
    public boolean containsCRLF() {
        return input.endsWith("\\r\\n");
    }

    public boolean inputTooLong() {
        // Maximum message length for IRC is 512 bytes
        return input.length() > IRC.Constants.MAXLENGTH;
    }
}