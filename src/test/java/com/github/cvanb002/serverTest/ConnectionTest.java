package com.github.cvanb002.serverTest;

import com.github.cvanb002.irc.CommandHandler;
import com.github.cvanb002.irc.Parser;
import com.github.cvanb002.model.Client;
import com.github.cvanb002.model.State;
import com.github.cvanb002.server.Connection;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConnectionTest {
    @Test
    public void clientReceivesCorrectData() throws IOException {
        try
        {
            StringWriter stringWriter = new StringWriter();
            BufferedWriter out = new BufferedWriter(stringWriter);
            State state = new State();
            Client client = new Client();
            Connection connection = new Connection(out, new BufferedReader(new StringReader("")), new Parser(), new CommandHandler(state), client);

            connection.send(":alice!alice@host PRIVMSG TEST");
            String message = stringWriter.toString();
            assertEquals(":alice!alice@host PRIVMSG TEST\r\n", message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
