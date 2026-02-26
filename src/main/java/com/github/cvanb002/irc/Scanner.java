package com.github.cvanb002.irc;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Scanner{
    String input = "";
    List<String> tokens = new ArrayList<>();

    public Scanner(String input){
        this.input = input;
    }

    public boolean checkCRLF(){
        return input.endsWith("\\r\\n");
    }

    public boolean exceedsLength(){
        // Maximum message length for IRC is 512 bytes
        return input.length() < IRC.Constants.MAXLENGTH ;
    }

    public String stripCRLF(){
        if (checkCRLF()){
            return input.substring(0, input.length() - 4);
        } throw new InputMismatchException("No CRLF");
    }



}