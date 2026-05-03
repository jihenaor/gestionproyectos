package com.gestionproyectos.adapter.inbound.rest.controller.dto;

public record TokenResponse(
        String tokenType,
        String accessToken,
        long expiresIn
) {
    public TokenResponse(String accessToken, long expiresIn) {
        this("Bearer", accessToken, expiresIn);
    }
}