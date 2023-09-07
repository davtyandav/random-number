package com.randomNumber.davDavtyan;

import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

/**
 * A simple WebSocket Server implementation. sending random number.
 */
public class RandomNumberServer extends WebSocketServer {
    private final Set<String> connectedUsers = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final int port;
    private final Set<String> numbers = new HashSet<>();
    private final static SecureRandom RANDOM = new SecureRandom();

    public RandomNumberServer(int port) {
        super(new InetSocketAddress(port));
        this.port = port;
    }

    @Override
    public void onStart() {
        System.out.println("RandomNumber Server started on port: " + port);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        // Get the IP address of the connected client
        String ip = extractIp(conn);

        // Check if a connection from the same IP address already exists
        if (!connectedUsers.add(ip)) {
            // If a connection from the same IP exists, close the new connection with a status code 409 (Conflict)
            conn.close(409, "Only one connection from the same IP address is allowed");
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        String ip = extractIp(conn);
        connectedUsers.remove(ip);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        String randomNumber = getUniqueNumber();
        numbers.add(randomNumber);
        conn.send("{\"randomNumber \":\"" + randomNumber + "\"}");
    }

    /**
     * Generates a unique random number as a string.
     * This method ensures that the generated number is not already present in the 'numbers' set.
     *
     * @return A unique random number as a string.
     */
    private String getUniqueNumber() {
        // Generate an initial random number
        String randomNumber = generateNumber();

        // Check if the generated number is already in the 'numbers' set
        while (numbers.contains(randomNumber)) {
            // If it's not unique, generate a new random number until a unique one is found
            randomNumber = generateNumber();
        }

        // Return the unique random number
        return randomNumber;
    }

    /**
     * Generates a random BigInteger as a string.
     * This method uses a BigInteger constructor to create a random BigInteger
     * with the specified bit length using the provided random source.
     *
     * @return A random BigInteger as a string.
     */
    private String generateNumber() {
        // Generate a random BigInteger with the specified bit length
        BigInteger randomBigInteger = new BigInteger(256, RANDOM);

        // Convert the BigInteger to a string
        return randomBigInteger.toString();
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    private String extractIp(WebSocket webSocket) {
        return webSocket.getRemoteSocketAddress().getAddress().toString();
    }

}
