package com.github.cvanb002.irc;

import com.github.cvanb002.irc.handlers.*;
import com.github.cvanb002.model.Handler;
import com.github.cvanb002.model.State;

// Class containing data pertinent to IRC spec
public class IRC {
    public static class Constants {
        public static int MAXLENGTH = 512;
        public static char SEPERATOR = ' ';
        public static String CRLF = "\r\n";
    }

    public enum Numeric {
        ERR_INPUT_TOO_LONG(417),
        ERR_UNKNOWNERROR(400);

        private final int code;

        Numeric(int code){
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum Type {
        SOURCE,
        COMMAND,
        PARAMETER,
        EXTPARAMETER,

    }

    // This part of IRC gets updated each time a new command is implemented, new commands must both be added to the enum
    // and the create factory to ensure that commandHandler can execute them.
    public enum Commands {
        PING(PingHandler.class),
        NICK(NickHandler.class),
        USER(UserHandler.class),
        QUIT(QuitHandler.class),
        JOIN(JoinHandler.class),
        PRIVMSG(PrivMsgHandler.class);

        final Class<?> commandClass;

        Commands(Class<?> commandClass) {
            this.commandClass = commandClass;
        }

        // Factory for creating implemented command classes
        public Handler create(State state){
            if (commandClass.equals(PingHandler.class)){
                return new PingHandler(state);
            } else if (commandClass.equals(NickHandler.class)) {
                return new NickHandler(state);
            } else if (commandClass.equals(UserHandler.class)) {
                return new UserHandler(state);
            } else if(commandClass.equals(QuitHandler.class)){
                return new QuitHandler(state);
            } else if(commandClass.equals(PrivMsgHandler.class)){
                return new PrivMsgHandler(state);
            } else if(commandClass.equals(JoinHandler.class)){
                return new JoinHandler(state);
            }
            return null;
        }
        

    }


}