package com.randomNumber.davDavtyan;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class represents the entry point for starting the WebSocket server.
 */
public class StartServer {

    public static void main(String[] args) throws IOException {
        // Get the path to the 'property.txt' file
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("property.txt").getPath();

        // Load server configuration properties from 'property.txt' file
        Properties defaultProps = new Properties();
        InputStream inputStream = new FileInputStream(rootPath);

        defaultProps.load(inputStream);

        // Get the port number from the properties file
        String port = defaultProps.get("port").toString();

        // Create and start the RandomNumberServer on the specified port
        RandomNumberServer server = new RandomNumberServer(Integer.parseInt(port));
        server.start();
    }
}
