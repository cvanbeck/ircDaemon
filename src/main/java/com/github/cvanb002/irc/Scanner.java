package com.github.cvanb002.irc;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Scanner{

    String input = "";
    List<String> tokens = new ArrayList<>();

    int start = 0;
    int current = 0;

    public Scanner(String input){
        this.input = input;
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
    }

    public boolean checkCRLF(){
        return input.endsWith("\\r\\n");
    }

    public boolean inputTooLong(){
        // Maximum message length for IRC is 512 bytes
        return input.length() < IRC.Constants.MAXLENGTH ;
    }

    public String stripCRLF(){
        if (checkCRLF()){
            return input.substring(0, input.length() - 4);
        } throw new InputMismatchException("No CRLF");
    }



}