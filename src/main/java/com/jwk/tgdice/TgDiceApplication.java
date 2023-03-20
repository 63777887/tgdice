package com.jwk.tgdice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.jwk.tgdice.biz.dao")
@EnableScheduling
@EnableTransactionManagement
public class TgDiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TgDiceApplication.class, args);
    }

}
