This software creates a server that implements the IRC specification, providing real-time chat between multiple concurrent users. Users are able to message each other directly, or create chat rooms for group discussion.
# Core Features
- Server network code that: Listens for incoming connections and creates a socket for each connection, assigning each connection its own thread. Meaning multiple sockets are able to communicate at the same time
- An IRC parser capable of breaking down incoming messages into their constituent parts
- Real-time chat between users based on the IRC spec
- Support for the creation of channels: allowing users to create chat rooms which enable users to communicate with groups of other users.
- The current implemented commands are:
	- PRIVMSG, used to either send a message to another client or to send a message to a channel
	- JOIN, used to either join an existing channel or create a new one
	- PING, USER, NICK, and QUIT. Used automatically by IRC client applications to interact with a server.

# Dependencies and Installation Guide
This application requires
- `Apache Maven 3.9.12+`
- `Java version: 17+`

You will also require an external IRC client to connect to the server, options have been provided in the setup section below.

Use the following command to compile the application and install all other dependencies.
`mvn clean install`

# Setup and Run Instructions
Once the server has been compiled the server can be started by running the following command from the root directory:
`java -cp target/ircDaemon-1.jar com.github.cvanb002.ircDaemon`

This creates the server which will then listen for any incoming connections. To actually interact with the server you must install an external client, whilst any client will work I suggest the following client.

Essentially however, connecting to the server boils down to telling the client to connect to localhost at port 6665.
- Halloy: https://halloy.chat/

Once the server is up and running it will log all received and outgoing messages inside the terminal it is being run from.

# Known limitations / what is not implemented yet
Currently this application only provides the minimum commands required to provide chat between multiple users and does not yet provide the tools for channel/message moderation. Future implementations of this project would include further support for this. The current supported commands are:
- PING
- PRIVMSG
- JOIN
- USER
- NICK
- QUIT

Finally, the server currently only runs on localhost. This is less a lack of implementation and more for the sake of testing, if you would like to see a version capable of listening to connections from external networks then this is available on the outside-server-test branch on GitHub.