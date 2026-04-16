package com.github.cvanb002.irc.handlers;

import com.github.cvanb002.model.Client;
import com.github.cvanb002.model.Handler;
import com.github.cvanb002.model.Message;
import com.github.cvanb002.model.State;

public class JoinHandler extends Handler {
    State state;

    public JoinHandler(State state){
        this.state = state;
    }

    @Override
    public void call(Message message, Client client) {
        
    }
}
