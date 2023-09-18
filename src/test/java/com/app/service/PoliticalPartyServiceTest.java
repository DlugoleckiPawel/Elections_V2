package com.app.service;

import com.app.dto.PoliticalPartyDto;
import com.app.model.PoliticalParty;
import com.app.repository.PoliticalPartyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PoliticalPartyServiceTest {

    @Mock
    PoliticalPartyRepository politicalPartyRepository;

    PoliticalPartyService politicalPartyService;

    @BeforeEach
    void setUp() {
        politicalPartyService = new PoliticalPartyService(politicalPartyRepository);
    }

    @Test
    void shouldCreatePoliticalParty() {
        // given
        PoliticalPartyDto dto = new PoliticalPartyDto();
        PoliticalParty entity = new PoliticalParty();

        when(politicalPartyRepository.save(any(PoliticalParty.class))).thenReturn(entity);

        // when
        PoliticalPartyDto result = politicalPartyService.createPoliticalParty(dto);

        // then
        assertNotNull(result);
        assertEquals(result, dto);
        verify(politicalPartyRepository, times(1)).save(any(PoliticalParty.class));
    }

    @Test
    void shouldUpdatePoliticalParty() {
        // given
        PoliticalParty entity = PoliticalParty
                .builder()
                .id(1L)
                .name("test")
                .build();

        when(politicalPartyRepository.existsById(1L)).thenReturn(true);
        when(politicalPartyRepository.findById(1L)).thenReturn(Optional.of(entity));

        PoliticalPartyDto dto = PoliticalPartyDto
                .builder()
                .id(1L)
                .name("new_name")
                .build();

        // when
        PoliticalPartyDto result = politicalPartyService.updatePoliticalParty(dto);

        // then
        assertEquals(result, dto);
        assertEquals(result.getName(), "new_name");
        verify(politicalPartyRepository, times(1)).save(entity);
    }

    @Test
    void shouldGetAllPoliticalParty() {
        // given
        PoliticalParty entity1 = new PoliticalParty();
        PoliticalParty entity2 = new PoliticalParty();
        List<PoliticalParty> all = Arrays.asList(entity1, entity2);

        when(politicalPartyRepository.findAll()).thenReturn(all);

        // when
        List<PoliticalPartyDto> result = politicalPartyService.getAllParties();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(politicalPartyRepository, times(1)).findAll();
    }

    @Test
    void shouldGetPoliticalPartyByIdExists() {
        // given
        Long id = 1L;
        PoliticalParty entity = new PoliticalParty();

        when(politicalPartyRepository.findById(id)).thenReturn(Optional.of(entity));

        // when
        PoliticalPartyDto result = politicalPartyService.getPoliticalPartyById(id);

        // then
        assertNotNull(result);
        verify(politicalPartyRepository, times(1)).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenConstituencyNotFoundById() {
        // given
        Long id = 1L;

        when(politicalPartyRepository.findById(id)).thenReturn(Optional.empty());

        // when
        assertThrows(RuntimeException.class, () ->
                politicalPartyService.getPoliticalPartyById(id));

        // then
        verify(politicalPartyRepository, times(1)).findById(id);
    }

    @Test
    void shouldDeleteConstituencyById() {
        // given
        Long id = 1L;
        doNothing().when(politicalPartyRepository).deleteById(id);

        // when
        politicalPartyService.deletePoliticalPartyById(id);

        // then
        verify(politicalPartyRepository, times(1)).deleteById(id);
    }
}