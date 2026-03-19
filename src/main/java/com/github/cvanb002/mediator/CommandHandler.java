package com.github.cvanb002.mediator;


import com.github.cvanb002.irc.IRC;

public class CommandHandler {
    // Module for carrying out IRC commands
    public void user(){};

    public void ping(String source, String token){
        if(token.isEmpty()){
            return IRC.Numeric.ERR_NEEDMOREPARAMS;
        }
    };

    public void pong(){};

    public void oper(){};

    public void quit(){};

    public void error(){};

    public void join(){};

    public void kick(){};

    public void privmsg(){};

}
