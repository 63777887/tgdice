package com.jwk.tgdice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jiwk
 * @version 0.1.0
 * <p>
 * @date 2023/3/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BetEntity {
    Integer special;
    String betType;
    String playType;
    Integer value;
}
