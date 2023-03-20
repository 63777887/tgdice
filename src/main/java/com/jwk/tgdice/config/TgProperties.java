package com.jwk.tgdice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("jwk.bot")
@Component
public class TgProperties {
    String botUsername;
    String botToken;
}
