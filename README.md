Pre-requirements:
- Java 16
- Maven 3 OR wscat
- Port 8887 should not be used by any program - it is used by WebSocket server

wscat installation:
- sudo apt install nodejs
- sudo apt install npm
- sudo npm install -g wscat

To run WebSocket server go to 'bin' folder and open a terminal (or you could use maven and build your own version of jar file). Then execute following command:
java -cp websocket-1.0-jar-with-dependencies.jar com.randomNumber.davDavtyan.StartServer

For client testing purposes you could use following ways:
1) Use postman. Press Ctrl+N and create WebSocketRequest
2) Use wscat

To use wscat open second terminal's tab you could run wscat as follow:
wscat -c ws://localhost:8887
Then you could press Enter key or sent some random message and you will get a result with randomNumber - something like this:
{"randomNumber ":"7675587084077361548337186203522716630597932931446970669934189405219217544250"}

If you open another tab and try other wscat command it will terminate and server will show an error, because you already connected from the current IP address.