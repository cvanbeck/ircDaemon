package com.github.cvanb002.irc.handlers;

import com.github.cvanb002.model.Client;
import com.github.cvanb002.model.Handler;
import com.github.cvanb002.model.Message;
import com.github.cvanb002.model.State;

public class PrivMsgHandler extends Handler {
    State state;

    public PrivMsgHandler(State state) {
        this.state = state;
    }

    @Override
    public void call(Message message, Client client) {
        return;
    }
}
