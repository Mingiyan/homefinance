package ru.geekfactory.homefinance.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.geekfactory.homefinance.dao.config.DaoConfig;

@Configuration
@Import(value = DaoConfig.class)
@ComponentScan("ru.geekfactory.homefinance.service")
public class ServiceConfig {
}
