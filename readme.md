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

Whilst I believe this application should run on operating system, it has only been confirmed to work on Windows and Linux.

You will also require an external IRC client to connect to the server, options have been provided in the setup section below.

Use the following command to compile the application and install all other dependencies.
`mvn clean install`

# Setup and Run Instructions
Once the server has been compiled the server can be started by running the following command from the root directory:
`java -cp target/ircDaemon-1.jar com.github.cvanb002.ircDaemon`

This creates the server which will then listen for any incoming connections. To actually interact with the server you must install an external client, I suggest installing multiple of these to show that different clients are able to interact with the server and communicate with each other.  Whilst any client will work I I have provided detailed instructions on how to connect to the server with the following two clients below. Essentially however, connecting to the server boils down to telling the client to connect to localhost at port 6665.
- KVIrc: https://www.kvirc.net/?id=download&lang=en
- Halloy: https://halloy.chat/

These are the two main external clients I have been using for testing purposes, despite this I highly suggest installing other clients I haven't tested to fully demonstrate the separation between client and server. Several more clients can be found at https://libera.chat/guides/clients

Once the server is up and running it will log all received and outgoing messages inside the terminal it is being run from.
## Connecting/Using KVIrc
When installing KVIrc each option can be left as default, the only option you will need to change is the nickname. Keep note of what nickname you use as this is required for other clients to send you messages.
![[Pasted image 20260428215546.png]]

New connections can be created by first clicking the following option in the top left corner
![[Pasted image 20260428213902.png]]
This will then open the following screen:
![[Pasted image 20260428213954.png]]

From this screen you click the new network button on the right hand side, once you have done this you then click the 'New Server' button directly below.

After this you need to update the default configuration so that it connects to `127.0.0.1:6665`. To do this first change the server address from `irc.unknown.net` to `127.0.0.1` 
1. ![[Pasted image 20260428214435.png]]
2. ![[Pasted image 20260428214731.png]]

After this, click the `Advanced...` button to the right. On the screen that popups up, select the `Connection` tab and set the port is set to 6665. After this select the green `Connect Now` button to connect to the server.
![[Pasted image 20260428214837.png]]

If you have connected correctly then the main screen should have the following dialog:
![[Pasted image 20260428215055.png]]

After you have done this send any message from the textbox at the bottom to finish connecting.

A successful connection should look like the following
![[Pasted image 20260428220038.png]]

To join/create a channel you can either click this button at the top or send the message `JOIN #channelName`

Once you have joined a channel it will open a new area which can be used to send messages to all connected clients, the KVIrc will automatically convert your sent message into the correct IRC format and send it to the server.

To communicate directly with other users, you can use `PRIVMSG userNick :Message to send` where userNick is the nickname of another client and the message to send is preceded by a semi colon. Once you have done this it will create a chatbox similar to the one created when joining a channel. Note that in order to send a message to another user, that user must exist within the server.

## Connecting/Using Halloy
Connecting with Halloy is much easier and instead requires you to adjust a singular config file before you connect. On first launch you should be asked if you'd like to adjust the config file, if this option does not appear then the config file can be found at `%AppData%\halloy` on windows.

Replace the config file with the following, note that nickname can be set to what ever name you'd like.
```
[servers.localhost]
server = "127.0.0.1"
port = 6665
use_tls = false

nickname = "myPC"
```

Once you have done this select the `Reload Config File` button and then on the new screen select `localhost` from the buffer on the left hand side.

The following commands can then be used to communicate with other users, both of these add new options in the buffer on the left which when selected open new panes which can be interacted with like any other chat application to send messages.

`/join` followed by the channel name to join/create a channel.
![[Pasted image 20260428222257.png]]

`/msg` followed by a user nickname and message to privately message a connected user.
![[Pasted image 20260428222526.png]]

# Known limitations / what is not implemented yet
Currently this application only provides the minimum commands required to provide chat between multiple users and does not yet provide the tools for channel/message moderation. Future implementations of this project would include further support for this.

 Another limitation in the current implementation is that a thread is created for each new connection/socket, meaning a low end pc may run into issues with a large number of connections. In the future a better alternative than having a new thread for each socket would be to make use of Java's `ThreadPoolExecutor` for more robust handling of threads.

Furthermore, the UI of this project has not yet been implemented. In the future I would like to add a cleaner way to run the project than directly running the compiled jar file, I would also of liked to add more robust logging. The project logs all incoming/outgoing messages straight into the console, a better way to handle this would be to have this stored in a txt file somewhere so that it can be read after the application has closed.

There are also several security limitations that I'd like to address in future implementations. For instance, if a user disconnects then their nickname can be taken by anyone meaning a user can impersonate another user. Other implementations of IRC servers rely on specialised clients called services, essentially automated bots, that provide features to protect user privileges.

Finally, the server currently only runs on localhost. This is less a lack of implementation and more for the sake of testing, if you would like to see a version capable of listening to connections from external networks then this is available on the outside-server-test branch on GitHub.