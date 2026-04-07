package com.github.cvanb002.model;

import com.github.cvanb002.irc.IRC;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private String source = "";
    private String command = "";
    private IRC.Numeric numeric;
    private final List<String> parameters = new ArrayList<String>();

    public Message(){}

    public Message(String command){
        this.command = command;
    }

    public Message(String command, List<String> parameters){
        this.command = command;
        this.parameters.addAll(parameters);
    }

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
        StringBuilder string = new StringBuilder();
        if(!source.isEmpty()){
            string.append(getSource()).append(" ");
        }
        if(!command.isEmpty()){
            string.append(getCommand()).append(" ");
        }
        if (!parameters.isEmpty()){
            for(String parameter : parameters){
                string.append(parameter).append(" ");
            }
        }
        return string.toString().strip();
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
