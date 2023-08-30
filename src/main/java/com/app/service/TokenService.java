package com.app.service;

import com.app.dto.TokenDto;
import com.app.dto.VoterDto;
import com.app.mapper.TokenMapper;
import com.app.mapper.VoterMapper;
import com.app.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
}
