package com.app.mapper;

import com.app.dto.TokenDto;
import com.app.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenMapper {
    private static final VoterMapper voterMapper = new VoterMapper();

    public static TokenDto toDto(Token token) {
        return token == null ? null : TokenDto
                .builder()
                .id(token.getId())
                .token(token.getToken())
                .expirationTime(token.getExpirationTime())
                .voterDto(token.getVoter() == null ? null : voterMapper.toDto(token.getVoter()))
                .build();
    }

    public static Token toEntity(TokenDto tokenDto) {
        return tokenDto == null ? null : Token
                .builder()
                .id(tokenDto.getId())
                .token(tokenDto.getToken())
                .expirationTime(tokenDto.getExpirationTime())
                .voter(tokenDto.getVoterDto() == null ? null : voterMapper.toEntity(tokenDto.getVoterDto()))
                .build();
    }
}
