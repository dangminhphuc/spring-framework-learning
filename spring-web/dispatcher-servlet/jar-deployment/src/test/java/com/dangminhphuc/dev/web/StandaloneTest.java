package com.dangminhphuc.dev.web;

import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StandaloneTest {
    private static Server server;

    @BeforeAll
    static void init() throws Exception {
        server = new Server(0);
        server.setHandler(App.createHandler());
        server.start();
    }

    @AfterAll
    static void clean() throws Exception {
        if (server.isRunning()) {
            server.stop();
        }
    }

    @Test
    void testHome() throws Exception {
        int port = server.getURI().getPort();
        URL url = new URL("http://localhost:" + port + "/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        assertEquals(200, con.getResponseCode());

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String body = in.readLine();
            assertEquals("Hello Web", body);
        }
    }

}
