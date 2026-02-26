package com.github.cvanb002.irc;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Scanner{

    String input = "";
    List<String> tokens = new ArrayList<>();

    private int start = 0;
    private int current = 0;

    public Scanner(String input){
        this.input = input;
    }

    public List<String> getTokens(){
        return tokens;
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

    public List<String> scanTokens() {
        while(!finished()){
            char c = input.charAt(current);

            if(c == IRC.Constants.EXTENDEDPARAM && !isSource(input.substring(current))){
                current++;
                tokens.add(input.substring(start).strip());
                return tokens;
            }

            if(c == IRC.Constants.SEPERATOR){
                tokens.add(input.substring(start, current).strip());
                start = current;
            }
            current++;
        }
        return tokens;
    }

    private boolean isSource(String input){
        return (tokens.isEmpty() || (tokens.size() == 1 && tokens.get(0).charAt(0) == IRC.Constants.TAGS));
    };

    private boolean finished(){
        return current >= input.length();
    };

    // TODO: Most of these functions should be private, so need a rewrite.
    public boolean containsCRLF(){
        return input.endsWith("\\r\\n");
    }

    public boolean inputTooLong(){
        // Maximum message length for IRC is 512 bytes
        return input.length() < IRC.Constants.MAXLENGTH ;
    }

    public String stripCRLF(){
        if (containsCRLF()){
            return input.substring(0, input.length() - 4);
        } throw new InputMismatchException("No CRLF");
    }

}