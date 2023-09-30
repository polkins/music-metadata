package com.polkins;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan(value = {"com.polkins.music.metadata"})
public class TestConfiguration {

}
