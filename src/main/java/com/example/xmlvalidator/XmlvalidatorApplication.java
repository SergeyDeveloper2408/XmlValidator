package com.example.xmlvalidator;

import com.example.xmlvalidator.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class XmlvalidatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(XmlvalidatorApplication.class, args);
    }

}