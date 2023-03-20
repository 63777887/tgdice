package com.jwk.tgdice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MybatisPlusConfig {



    @Bean
    @Primary//批量插入配置
    public MySqlInjector mySqlInjector() {
        return new MySqlInjector();
    }

}
