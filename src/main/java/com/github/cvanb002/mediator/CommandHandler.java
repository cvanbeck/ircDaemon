package com.github.cvanb002.mediator;


import com.github.cvanb002.irc.IRC;
import com.github.cvanb002.server.MessageRouter;

public class CommandHandler {
    // Module for carrying out IRC commands
    MessageRouter router;

    CommandHandler(MessageRouter router){
        this.router = router;
    }

    public void user(){};

    public IRC.Numeric ping(String source, String token){
        if(token.isEmpty()){
            return IRC.Numeric.ERR_NEEDMOREPARAMS;
        }
        else if(source.isEmpty()){
            return IRC.Numeric.ERR_NOORIGIN;
        }

        String message = constructMessage(IRC.Commands.PONG, token);
        router.sendMessage(source, message);
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
