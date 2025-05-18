package com.dangminhphuc.dev.resource;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class ConfigLoader {

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;
}
