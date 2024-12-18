package com.semo.semo.domain.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor
public class AuthToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}