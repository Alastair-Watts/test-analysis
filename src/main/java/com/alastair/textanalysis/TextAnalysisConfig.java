package com.alastair.textanalysis;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan
@PropertySource("classpath:/application.properties")
@EnableScheduling
public class TextAnalysisConfig {

}
