package com.github.cvanb002.mediator;


import com.github.cvanb002.irc.IRC;
import com.github.cvanb002.server.Client;
import com.github.cvanb002.server.MessageRouter;

import java.util.List;

public class CommandHandler {
    // Module for carrying out IRC commands
    MessageRouter router;

    CommandHandler(MessageRouter router){
        this.router = router;
    }

    /* public IRC.Numeric nick(Client client, List<String> parameters){
        if(nickInUse()){
            return IRC.Numeric.ERR_NICKNAMEINUSE;
        }
        if(!validNick()){
            return IRC.Numeric.ERR_ERRONEUSNICKNAME;
        }
        if(noNick()){
            return IRC.Numeric.NONICKNAMEGIVEN;
        }
        client.setNick(parameters.get(0));
        return IRC.Numeric.SUCCESS;
    }; */

    public void user(){};

    public IRC.Numeric ping(String source, String token){
        if(token.isEmpty()){
            return IRC.Numeric.ERR_NEEDMOREPARAMS;
        }
        else if(source.isEmpty()){
            return IRC.Numeric.ERR_NOORIGIN;
        }

        return IRC.Numeric.SUCCESS;
    };

    public void pong(){};

    public void oper(){};

    public void quit(){};

    public void error(){};

    public void join(){};

    public void kick(){};

    public void privmsg(){};

}
