package com.randomNumber.davDavtyan;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class StartServer {
    static Map<String, String> properties = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        initProperties();
        RandomNumberServer s = new RandomNumberServer(Integer.parseInt(properties.get("port")));
        s.start();
    }

    private static void initProperties() throws FileNotFoundException {
        String fileName = "/home/david/IdeaProjects/randomNumber/src/main/resources/property.txt";
        InputStream inputStream = new FileInputStream(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(":");
                String key = split[0].trim();
                String value = split[1].trim();
                properties.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
