package com.dangminhphuc.dev.resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleResourceTest {
    private static final String URL = "https://raw.githubusercontent.com/dangminhphuc/dm-spring/main/README.md";

    @Test
    @DisplayName("Test get resource by url")
    void testUrlResource() throws IOException {
        Resource resource = new UrlResource(URL);

        String content = content(resource);

        assertNotNull(content);
        assertTrue(content.contains("Spring"));
    }

    private static String content(Resource resource) throws IOException {
        try (InputStream is = resource.getInputStream()) {
            return new String(is.readAllBytes());
        }
    }
}