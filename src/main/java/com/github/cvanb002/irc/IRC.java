package com.github.cvanb002.irc;

public class IRC {

    public static class Constants {
        public static int MAXLENGTH = 512;
        public static char SEPERATOR = ' ';
        public static char EXTENDEDPARAMPREPEND = ':';
        public static char TAGSPREPEND = '@';
        public static char SOURCEPREPEND = ':';
    }

    public enum Numeric {
        SUCCESS(-1), // Not an actual IRC command, use to confirm to server success,
        ERR_INPUT_TOO_LONG(417),
        ERR_UNKNOWNERROR(400),
        ERR_NEEDMOREPARAMS(412),
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
        NULL
    }

    public enum Commands {
        PRIVMSG,
        USER,
        PING,
        PONG,
        OPER,
        QUIT,
        ERROR,
        JOIN,
        KICK
    }


}