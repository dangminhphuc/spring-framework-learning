package com.dangminhphuc.dev.i18n;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlConfigurationI18nTest {
    private static final Locale EN_LOCATE = Locale.ENGLISH;
    private static final Locale VI_LOCATE = new Locale("vi");

    private MessageSource context;

    @BeforeEach
    public void beforeEach() {
        this.context = new ClassPathXmlApplicationContext("application-context.xml");
    }

    @Test
    public void testLocateWithEnglish() {
        String message = this.context.getMessage("message", null, "", EN_LOCATE);
        assertEquals("Hello", message);
    }

    @Test
    public void testLocateWithVietnam() {
        String message = this.context.getMessage("message", null, "", VI_LOCATE);
        assertEquals("Xin chao", message);
    }

    @Test
    public void testDefaultMessage() {
        String message = this.context.getMessage("NOT_IN_PROPERTIES", null, "Default message", VI_LOCATE);
        assertEquals("Default message", message);
    }

    @Test
    public void testLocateWithArgs() {
        String message = this.context.getMessage("argument.required", new Object[]{"username", "password"}, "", VI_LOCATE);
        assertEquals("The username argument is required.", message);
    }
}
