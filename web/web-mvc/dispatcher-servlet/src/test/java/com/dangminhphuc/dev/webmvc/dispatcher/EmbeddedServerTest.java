package com.dangminhphuc.dev.webmvc.dispatcher;

import com.dangminhphuc.dev.webmvc.dispatcher.config.DeclarativeConfig;
import com.dangminhphuc.dev.webmvc.dispatcher.config.ProgrammaticConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class EmbeddedServerTest {

    @Nested
    @ContextConfiguration(classes = ProgrammaticConfig.class)
    @ExtendWith(SpringExtension.class)
    @WebAppConfiguration
    class JavaDispatcher {
        @Autowired
        private EmbeddedJettyServer embeddedServer;

        @BeforeEach
        void init() throws Exception {
            embeddedServer.start();
        }

        @AfterEach
        void clean() throws Exception {
            if (embeddedServer != null) {
                embeddedServer.stop();
            }
        }

        @Test
        void testHelloEndpoint() throws Exception {
            int port = embeddedServer.getPort();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:" + port + "/app/hello"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Assertions.assertEquals(200, response.statusCode());
            Assertions.assertEquals("Hello, Spring MVC!", response.body());
        }
    }

    @Nested
    @ContextConfiguration(locations = "classpath:app-config.xml")
    @ExtendWith(SpringExtension.class)
    @WebAppConfiguration
    class XmlWebDispatcher {
        @Autowired
        private EmbeddedJettyServer embeddedServer;

        @Qualifier("xmlServletInitializer")
        private ServletInitializer servletInitializer;

        @BeforeEach
        void init() throws Exception {
            embeddedServer.start();
        }

        @AfterEach
        void clean() throws Exception {
            if (embeddedServer != null) {
                embeddedServer.stop();
            }
        }

        @Test
        void testHelloEndpoint() throws Exception {
            int port = embeddedServer.getPort();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:" + port + "/app/hello"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Assertions.assertEquals(200, response.statusCode());
            Assertions.assertEquals("Hello, Spring MVC!", response.body());
        }
    }


    @Nested
    @ContextConfiguration(classes = DeclarativeConfig.class)
    @ExtendWith(SpringExtension.class)
    @WebAppConfiguration
    class AnnotationWebDispatcher {
        @Autowired
        private EmbeddedJettyServer embeddedServer;

        @Qualifier("annotationServletInitializer")
        private ServletInitializer servletInitializer;

        @BeforeEach
        void init() throws Exception {
            embeddedServer.start();
        }

        @AfterEach
        void clean() throws Exception {
            if (embeddedServer != null) {
                embeddedServer.stop();
            }
        }

        @Test
        void testHelloEndpoint() throws Exception {
            int port = embeddedServer.getPort();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:" + port + "/app/hello"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Assertions.assertEquals(200, response.statusCode());
            Assertions.assertEquals("Hello, Spring MVC!", response.body());
        }
    }
}
