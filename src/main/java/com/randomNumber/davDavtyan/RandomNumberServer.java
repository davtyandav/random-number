package com.randomNumber.davDavtyan;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
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
        String ip = extractIp(conn);
        if (!connectedUsers.add(ip)) {
            conn.close(409, "Only one connection from same ip is allowed");
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        String ip = extractIp(conn);
        connectedUsers.remove(ip);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        String randomNumber = generateNumber();
        while (numbers.contains(randomNumber)) {
            randomNumber = generateNumber();
        }
        numbers.add(randomNumber);
        conn.send("{\"randomNumber \":\"" + randomNumber + "\"}");
    }

    private static String generateNumber() {
        UUID uuid = UUID.randomUUID();
        long mostSignificantBits = Math.abs(uuid.getMostSignificantBits());
        long leastSignificantBits = Math.abs(uuid.getLeastSignificantBits());
        return "" + mostSignificantBits + leastSignificantBits;
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    private String extractIp(WebSocket webSocket) {
        return webSocket.getRemoteSocketAddress().getAddress().toString();
    }

}
