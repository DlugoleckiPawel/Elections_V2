package com.app.service;

import com.app.dto.TokenDto;
import com.app.dto.VoterDto;
import com.app.exception.TokenExpiredException;
import com.app.exception.TokenNotFoundException;
import com.app.mapper.TokenMapper;
import com.app.model.Token;
import com.app.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenDto createTokenForVoter(VoterDto voterDto) {
        String tokenValue = RandomStringUtils.randomNumeric(4);

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(tokenValue);
        tokenDto.setVoterDto(voterDto);
        tokenDto.setExpirationTime(LocalDateTime.now().plusHours(24));

        tokenRepository.save(TokenMapper.toEntity(tokenDto));
        log.info("Dodano nowy token: " + tokenDto);
        return tokenDto;
    }

    public boolean isTokenValidForToken(VoterDto voterDto, String token) {
        Optional<Token> tokenFromDb = tokenRepository.findByVoterId(voterDto.getId());

        Token tokenFromVoter = tokenFromDb.get();

        if (!tokenFromVoter.getToken().equals(token)) {
            throw new TokenNotFoundException();
        }

        if (LocalDateTime.now().isAfter(tokenFromVoter.getExpirationTime())) {
            throw new TokenExpiredException();
        }

        return true;
    }
}
