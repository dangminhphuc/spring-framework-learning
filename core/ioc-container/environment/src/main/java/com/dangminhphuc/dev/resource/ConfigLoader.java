package com.dangminhphuc.dev.resource;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component  // @Component used for annotation config, unless with XML configuration
public class ConfigLoader {
    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Autowired
    private String environmentName;
}
