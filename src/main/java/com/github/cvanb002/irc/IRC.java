package com.github.cvanb002.irc;

public class IRC {

    public static class Constants {
        public static int MAXLENGTH = 512;
        public static char SEPERATOR = ' ';
        public static char EXTENDEDPARAMPREPEND = ':';
        public static char TAGSPREPEND = '@';
        public static char SOURCEPREPEND = ':';
    }

    public enum Numerics {
        SUCCESS,
        ERR_INPUT_TOO_LONG,
        ERR_UNKNOWNERROR
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