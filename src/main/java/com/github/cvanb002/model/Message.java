package com.github.cvanb002.model;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private String source = "";
    private String command = "";
    private List<String> parameters = new ArrayList<>();

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

    public String getCommand() {
        return command;
    }

    public String getSource() {
        return source;
    }

    public List<String> getParameters() {
        return parameters;
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
