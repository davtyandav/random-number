package com.randomNumber.davDavtyan;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class represents the entry point for starting the WebSocket server.
 */
public class StartServer {

    public static void main(String[] args) throws IOException {
        // Get the path to the 'property.txt' file

        try (InputStream inputStream = StartServer.class.getClassLoader().getResourceAsStream("property.txt")) {
            // Load server configuration properties from 'property.txt' file
            Properties defaultProps = new Properties();

            defaultProps.load(inputStream);

            // Get the port number from the properties file
            String port = defaultProps.get("port").toString();

            // Create and start the RandomNumberServer on the specified port
            RandomNumberServer server = new RandomNumberServer(Integer.parseInt(port));
            server.start();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}


