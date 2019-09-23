package ru.geekfactory.homefinance.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.geekfactory.homefinance.config.ServiceConfig;

@Configuration
@EnableWebMvc
@Import(value = ServiceConfig.class)
public class WebConfig implements WebMvcConfigurer {

}
