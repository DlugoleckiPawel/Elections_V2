package com.app.service;

import com.app.dto.ConstituencyDto;
import com.app.model.Constituency;
import com.app.repository.ConstituencyRepository;
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
class ConstituencyServiceTest {

    @Mock
    ConstituencyRepository constituencyRepository;

    ConstituencyService constituencyService;

    @BeforeEach
    void setUp() {
        constituencyService = new ConstituencyService(constituencyRepository);
    }

    @Test
    void shouldCreateConstituency() {
        // given
        ConstituencyDto dto = new ConstituencyDto();
        Constituency entity = new Constituency();

        when(constituencyRepository.save(any(Constituency.class))).thenReturn(entity);

        // when
        ConstituencyDto result = constituencyService.createConstituency(dto);

        // then
        assertNotNull(result);
        assertEquals(result, dto);
        verify(constituencyRepository, times(1)).save(any(Constituency.class));
    }

    @Test
    void shouldUpdateConstituency() {
        // given
        Constituency entity = Constituency
                .builder()
                .name("name")
                .build();

        when(constituencyRepository.existsById(1L)).thenReturn(true);
        when(constituencyRepository.findById(1L)).thenReturn(Optional.of(entity));

        ConstituencyDto dto = ConstituencyDto
                .builder()
                .id(1L)
                .name("new_name")
                .build();


        // when
        ConstituencyDto result = constituencyService.updateConstituency(dto);

        // then
        assertEquals(result, dto);
        assertEquals(result.getName(), "new_name");
        verify(constituencyRepository, times(1)).save(entity);
    }

    @Test
    void shouldGetAllConstituencies() {
        // given
        Constituency entity1 = new Constituency();
        Constituency entity2 = new Constituency();
        List<Constituency> all = Arrays.asList(entity1, entity2);

        when(constituencyRepository.findAll()).thenReturn(all);

        // when
        List<ConstituencyDto> result = constituencyService.getAllConstituencies();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(constituencyRepository, times(1)).findAll();
    }

    @Test
    void shouldGetConstituencyByIdExists() {
        // given
        Long id = 1L;
        Constituency entity = new Constituency();
        when(constituencyRepository.findById(id)).thenReturn(Optional.of(entity));

        // when
        ConstituencyDto result = constituencyService.getConstituencyById(id);

        // then
        assertNotNull(result);
        verify(constituencyRepository, times(1)).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenConstituencyNotFoundById() {
        // given
        Long id = 1L;
        when(constituencyRepository.findById(id)).thenReturn(Optional.empty());

        // when
        assertThrows(RuntimeException.class, () ->
                constituencyService.getConstituencyById(id));

        // then
        verify(constituencyRepository, times(1)).findById(id);
    }

    @Test
    void shouldDeleteConstituencyById() {
        // given
        Long id = 1L;
        doNothing().when(constituencyRepository).deleteById(id);

        // when
        constituencyService.deleteConstituencyById(id);

        // then
        verify(constituencyRepository, times(1)).deleteById(id);
    }
}