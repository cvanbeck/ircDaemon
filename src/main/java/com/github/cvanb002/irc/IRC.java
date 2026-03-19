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
        SUCCESS(999), // Not an actual IRC command, use to confirm to server success,
        ERR_INPUT_TOO_LONG(417),
        ERR_UNKNOWNERROR(400),
        ERR_NEEDMOREPARAMS(412);

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