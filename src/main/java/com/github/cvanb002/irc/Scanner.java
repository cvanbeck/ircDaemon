package com.github.cvanb002.irc;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Scanner{
    // TODO: THis implementation is a mess, defo needs a rework
    String input = "";
    List<Token> tokens = new ArrayList<>();

    private int start = 0;
    private int current = 0;

    public Scanner(String input){
        this.input = input;
    }


    public IRC.Numerics parse(){
        /* TODO: Need to rewrite this, I want to use enums for numerics but unsure if this is where I should add them */
        if(inputTooLong()){
            return IRC.Numerics.ERR_INPUT_TOO_LONG;
        }

        if(!containsCRLF()){
            return IRC.Numerics.ERR_UNKNOWNERROR;
        }

        scanTokens();
        return IRC.Numerics.SUCCESS;
    }

    public List<Token> scanTokens() {
        // TODO: Ugly function, redo later
        String token;
        while(!finished()){
            char c = input.charAt(current);

            if(c == IRC.Constants.EXTENDEDPARAMPREPEND && !isSource(input.substring(current))){
                token = input.substring(start).strip();
                addToken(token, IRC.Type.PARAMETER);
                return tokens;
            }

            if(c == IRC.Constants.SEPERATOR){
                token = input.substring(start, current).strip();

                if (isTag(token)){
                    addToken(token, IRC.Type.TAG);
                }
                else if (isSource(token)){
                    addToken(token, IRC.Type.SOURCE);
                }
                else if (isParameter()){
                    addToken(token, IRC.Type.PARAMETER);
                }
                else if (isCommand()){
                    addToken(token, IRC.Type.COMMAND);
                }
                else {
                    addToken (token, IRC.Type.NULL);
                }
                start = current;
            }
            current++;
        }

        return tokens;
    }

    private void addToken(String token, IRC.Type type){
        tokens.add(new Token(token, type));
    }

    private boolean isTag(String input){
        return (tokens.isEmpty() && input.charAt(0) == IRC.Constants.TAGSPREPEND);
    }

    private boolean isSource(String input){
        return (tokens.isEmpty() && input.charAt(0) == IRC.Constants.SOURCEPREPEND) ||
                (tokens.size() == 1 && contains(IRC.Type.TAG) && input.charAt(0) == IRC.Constants.SOURCEPREPEND);
    };

    private boolean isCommand(){
        return !contains(IRC.Type.COMMAND) &&
                (tokens.isEmpty() && !contains(IRC.Type.TAG) && !contains(IRC.Type.SOURCE)) ||
                (tokens.size() == 1 && contains(IRC.Type.TAG) || contains(IRC.Type.SOURCE)) ||
                (tokens.size() == 2 && contains(IRC.Type.TAG) && contains(IRC.Type.SOURCE));
    }

    private boolean isParameter(){
        System.out.println(tokens.size());
        return tokens.size() >= 1 && contains(IRC.Type.COMMAND);
    }

    private boolean contains(IRC.Type type){
        for(Token token : tokens){
            if(token.getType() == type){
                return true;
            }
        } return false;
    }

    private boolean finished(){
        return current >= input.length();
    };

    // TODO: Most of these functions should be private, so need a rewrite.
    public boolean containsCRLF(){
        return input.endsWith("\\r\\n");
    }

    public boolean inputTooLong(){
        // Maximum message length for IRC is 512 bytes
        return input.length() > IRC.Constants.MAXLENGTH ;
    }

    public String stripCRLF(){
        if (containsCRLF()){
            return input.substring(0, input.length() - 4);
        } throw new InputMismatchException("No CRLF");
    }
}