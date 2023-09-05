package com.app.service;

import com.app.dto.TokenDto;
import com.app.dto.VoterDto;
import com.app.exception.MaxAttemptReachedException;
import com.app.exception.TokenExpiredException;
import com.app.exception.TokenNotFoundException;
import com.app.mapper.TokenMapper;
import com.app.mapper.VoterMapper;
import com.app.model.Token;
import com.app.repository.TokenRepository;
import com.app.repository.VoterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final TokenRepository tokenRepository;
    private final VoterRepository voterRepository;
    private static final ConcurrentHashMap<Long, Integer> attempsMap = new ConcurrentHashMap<>();

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

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean isTokenValidForToken(VoterDto voterDto, String token) {

        Optional<Token> tokenFromDb = tokenRepository.findByVoterId(voterDto.getId());
        int attemps = attempsMap.getOrDefault(voterDto.getId(), 0);

        Token tokenFromVoter = tokenFromDb.get();

        if (!tokenFromVoter.getToken().equals(token)) {
            attemps++;
            attempsMap.put(voterDto.getId(), attemps);

            if (attemps >= 3) {
                voterDto.setHasVoted(true);
                voterRepository.save(VoterMapper.toEntity(voterDto));
                throw new MaxAttemptReachedException();
            }
            throw new TokenNotFoundException();
        }

        if (LocalDateTime.now().isAfter(tokenFromVoter.getExpirationTime())) {
            throw new TokenExpiredException();
        }
        return true;
    }
}
