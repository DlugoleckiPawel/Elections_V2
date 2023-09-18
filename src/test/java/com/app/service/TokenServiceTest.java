package com.app.service;

import com.app.dto.TokenDto;
import com.app.dto.VoterDto;
import com.app.exception.MaxAttemptReachedException;
import com.app.exception.TokenNotFoundException;
import com.app.model.Token;
import com.app.repository.TokenRepository;
import com.app.repository.VoterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
    @Mock
    TokenRepository tokenRepository;
    @Mock
    VoterRepository voterRepository;

    TokenService tokenService;

    @BeforeEach
    public void setUp() {
        tokenService = new TokenService(tokenRepository, voterRepository);
    }

    @Test
    public void shouldCreateTokenForVoter() {
        // given
        VoterDto voterDto = VoterDto.builder().id(1L).build();
        when(tokenRepository.save(any(Token.class))).thenAnswer(i -> i.getArgument(0));

        // when
        TokenDto result = tokenService.createTokenForVoter(voterDto);

        assertNotNull(result);
        assertEquals(voterDto.getId(), result.getVoterDto().getId());
        assertTrue(result.getExpirationTime().isAfter(LocalDateTime.now()));
    }

    @Test
    public void shouldCheckTokenValidity() {
        // given
        VoterDto voterDto = VoterDto.builder().id(1L).build();
        Token token = Token.builder().token("1234").expirationTime(LocalDateTime.now().plusHours(1)).build();
        when(tokenRepository.findByVoterId(voterDto.getId())).thenReturn(Optional.of(token));

        // when + then
        assertTrue(tokenService.isTokenValidForToken(voterDto, "1234"));
    }

    @Test
    public void shouldThrowMaxAttemptException() {
        // given
        VoterDto voterDto = VoterDto.builder().id(1L).build();
        Token token = Token.builder().token("1234").expirationTime(LocalDateTime.now().plusHours(1)).build();
        when(tokenRepository.findByVoterId(voterDto.getId())).thenReturn(Optional.of(token));

        // when + then
        assertThrows(TokenNotFoundException.class, () -> tokenService.isTokenValidForToken(voterDto, "4321"));
        assertThrows(TokenNotFoundException.class, () -> tokenService.isTokenValidForToken(voterDto, "4321"));
        assertThrows(MaxAttemptReachedException.class, () -> tokenService.isTokenValidForToken(voterDto, "4321"));


    }
}