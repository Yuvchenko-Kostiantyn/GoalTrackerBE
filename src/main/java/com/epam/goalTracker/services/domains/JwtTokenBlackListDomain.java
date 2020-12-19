package com.epam.goalTracker.services.domains;

import lombok.Data;

/**
 * DTO class for JWTtokenBlacklist
 *
 * @author Fazliddin Makhsudov
 */
@Data
public class JwtTokenBlackListDomain {

    private String token;
}
