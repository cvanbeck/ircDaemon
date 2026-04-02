package com.github.cvanb002.model;

import com.github.cvanb002.irc.IRC;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private String source = "";
    private String command = "";
    private IRC.Numeric numeric;
    private List<String> parameters = new ArrayList<String>();

    public Message(){

    }


    public Message(String command){
        this.command = command;
    }

    public Message(String command, List<String> parameters){
        this.command = command;
        this.parameters.addAll(parameters);
    };


    public Message(String source, String command, List<String> parameters){
        this.source = source;
        this.command = command;
        this.parameters.addAll(parameters);
    }

    public Message(IRC.Numeric numeric){
        // Used to check if there is an error with a message
        this.numeric = numeric;
    }
    public String getCommand() {
        return command;
    }

    public String getSource() {
        return source;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public IRC.Numeric getNumeric(){
        return numeric;
    }

    public String toString(){
        String string = "";
        if(!source.isEmpty()){
            string += getSource();
        }
        if(!command.isEmpty()){
            string += getCommand();
        }
        if (parameters.size() > 0){
            for(String parameter : parameters){
                string += parameter;
            }
        }
        return string;
    }

    public void addSource(String source){
        this.source = source;
    }

    public void addCommand(String command){
        this.command = command;
    }

    public void addParameter(String parameter){
        parameters.add(parameter);
    }



}
