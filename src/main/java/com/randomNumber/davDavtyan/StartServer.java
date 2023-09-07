package com.randomNumber.davDavtyan;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StartServer {

    public static void main(String[] args) throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("property.txt").getPath();

        Properties defaultProps = new Properties();
        InputStream inputStream = new FileInputStream(rootPath);

        defaultProps.load(inputStream);
        String port = defaultProps.get("port").toString();
        RandomNumberServer s = new RandomNumberServer(Integer.parseInt(port));
        s.start();
    }
}
