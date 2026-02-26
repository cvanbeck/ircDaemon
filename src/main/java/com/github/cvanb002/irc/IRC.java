package com.github.cvanb002.irc;

public class IRC {

    public static class Constants {
        public static int MAXLENGTH = 512;
        public static char SEPERATOR = ' ';
        public static char EXTENDEDPARAM= ':';
        public static char TAGS = '@';
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