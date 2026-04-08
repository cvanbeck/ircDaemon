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
        String tokenType;
        Message message = new Message();

        startIndex = 0;
        currentIndex = 0;

        while (!finishedScanning(input)) {
            char currentChar = input.charAt(currentIndex);

            if (currentChar == IRC.Constants.SEPERATOR || endOfLine(input)) {
                token = createToken(input);
                tokenType = categoriseToken(token, message);

                switch(tokenType) {
                    case "SOURCE":
                        message.addSource(token.substring(1));
                        break;
                    case "COMMAND":
                        message.addCommand(token);
                        break;
                    case "EXTPARAMETER":
                        // If an extended parameter is found we just take the rest of the message as a single param
                        token = createToken(input, startIndex+2);
                        message.addParameter(token);
                        return message;
                    case"PARAMETER":
                        // Regular params
                        message.addParameter(token);
                        break;
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

    private boolean finishedScanning(String input) {
        return currentIndex >= input.length();
    }

    private String createToken(String input, int startIndex){
        // Creates substring from start index onwards
        return input.substring(startIndex).strip();
    }

    private String createToken(String input){
        return input.substring(startIndex, currentIndex).strip();
    }


    private String categoriseToken(String token, Message message){
        if(token.charAt(0) == ':' && startIndex == 0){
            return IRC.Type.SOURCE.toString();
        }
        else if(message.getCommand().isEmpty()){
            return IRC.Type.COMMAND.toString();
        }
        else if ((token.charAt(0) == ':')){
            return IRC.Type.EXTPARAMETER.toString();
        } else {
            return IRC.Type.PARAMETER.toString();
        }
    }
}