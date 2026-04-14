package com.github.cvanb002.irc;

import com.github.cvanb002.irc.handlers.NickHandler;
import com.github.cvanb002.irc.handlers.PingHandler;
import com.github.cvanb002.model.Command;
import com.github.cvanb002.model.State;

public class IRC {
    public static class Constants {
        public static int MAXLENGTH = 512;
        public static char SEPERATOR = ' ';
        public static char EXTENDEDPARAMPREPEND = ':';
        public static char TAGSPREPEND = '@';
        public static char SOURCEPREPEND = ':';
        public static String CRLF = "\r\n";
    }

    public enum Numeric {
        SUCCESS(-1), // Not an actual IRC command, use to confirm to server success,
        ERR_INPUT_TOO_LONG(417),
        ERR_UNKNOWNERROR(400),
        ERR_NEEDMOREPARAMS(412),
        ERR_NOORIGIN(409),
        ERR_NICKNAMEINUSE(433),
        ERR_ERRONEUSNICKNAME(432),
        ERR_NONICKNAMEGIVEN(999),
        ERR_ALREADYREGISTERED(462),
        ERR_PASSWDMISMATCH(464),
        ERR_NOOPERHOST(491),
        RPL_YOUREOPER(381),
        ERR_NOSUCHCHANNEL(403),
        ERR_CHANOPRIVISNEEDED(482),
        ERR_USERNOTINCHANNEL(441),
        ERR_NOTONCHANNEL(442),
        ERR_NOSUCHNICK(401),
        ERR_NOSUCHSERVER(402),
        ERR_CANNOTSENDTOCHAN(404),
        ERR_NORECPIENT(411),
        ERR_NOTEXTTOSEND(412);

        private final int code;

        Numeric(int code){
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum Type {
        TAG,
        SOURCE,
        COMMAND,
        PARAMETER,
        EXTPARAMETER,
        NULL
    }

    public enum Commands {
        // PING command as temp so it compiles
        PRIVMSG(PingHandler.class),
        NICK(NickHandler.class),
        USER(PingHandler.class),
        PING(PingHandler.class),
        OPER(PingHandler.class),
        QUIT(PingHandler.class),
        ERROR(PingHandler.class),
        JOIN(PingHandler.class),
        KICK(PingHandler.class);

        final Class<?> commandClass;

        Commands(Class<?> commandClass) {
            this.commandClass = commandClass;
        }

        public Command create(State state){
            if (commandClass.equals(PingHandler.class)){
                return new PingHandler(state);
            } else if (commandClass.equals(NickHandler.class)) {
                return new NickHandler(state);
            }
            return null;
        }
        

    }


}