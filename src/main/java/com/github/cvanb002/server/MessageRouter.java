package com.github.cvanb002.server;

import com.github.cvanb002.irc.Scanner;
import com.github.cvanb002.model.Message;

import java.util.List;
import java.util.Objects;


public class MessageRouter {
    State state;

    public MessageRouter(State state){
        this.state = state;
    }
    
}
