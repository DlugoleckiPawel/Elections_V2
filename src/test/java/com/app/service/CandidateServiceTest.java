package com.app.service;

import com.app.dto.CandidateDto;
import com.app.dto.ConstituencyDto;
import com.app.dto.PoliticalPartyDto;
import com.app.dto.VoterDto;
import com.app.mapper.CandidateMapper;
import com.app.mapper.ConstituencyMapper;
import com.app.model.Candidate;
import com.app.model.Constituency;
import com.app.model.PoliticalParty;
import com.app.repository.CandidateRepository;
import com.app.repository.ConstituencyRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {

    @Mock
    CandidateRepository candidateRepository;
    @Mock
    ConstituencyRepository constituencyRepository;
    @Mock
    PoliticalPartyRepository politicalPartyRepository;
    @Mock
    CandidateMapper candidateMapper;
    private CandidateService candidateService;

    @BeforeEach
    void setUp() {
        candidateService = new CandidateService(
                candidateRepository,
                constituencyRepository,
                politicalPartyRepository
        );
    }

    @Test
    void shouldCreateCandidate() {
        // given
        Candidate candidate = Candidate.builder()
                .id(1L)
                .name("cand_name")
                .surname("cand_surname")
                .politicalParty(PoliticalParty.builder().id(1L).build())
                .constituency(Constituency.builder().id(1L).name("const_name").build()).build();

        CandidateDto dto = CandidateDto.builder().id(1L).build();

        ConstituencyDto constituencyDto = ConstituencyDto.builder().id(1L).build();
        PoliticalPartyDto politicalPartyDto = PoliticalPartyDto.builder().id(1L).build();

        dto.setConstituencyDto(constituencyDto);
        dto.setPoliticalPartyDto(politicalPartyDto);

        when(constituencyRepository.findById(1L)).thenReturn(Optional.of(candidate.getConstituency()));
        when(politicalPartyRepository.findById(1L)).thenReturn(Optional.of(candidate.getPoliticalParty()));
        when(candidateRepository.save(any(Candidate.class))).thenReturn(candidate);


        // when
        CandidateDto result = candidateService.createCandidate(dto);

        // then
        assertNotNull(result);
        assertNotNull(result.getConstituencyDto().getId());
        assertEquals(result.getConstituencyDto().getName(), "const_name");
        assertNotNull(result.getPoliticalPartyDto());
        verify(candidateRepository, times(1)).save(any(Candidate.class));
    }

    @Test
    void shouldUpdateCandidate() {
        // Given
        Long candidateId = 1L;
        Long politicalPartyId = 2L;
        Long constituencyId = 3L;

        CandidateDto candidateDto = new CandidateDto();
        candidateDto.setId(candidateId);

        PoliticalPartyDto politicalPartyDto = new PoliticalPartyDto();
        politicalPartyDto.setId(politicalPartyId);
        candidateDto.setPoliticalPartyDto(politicalPartyDto);

        ConstituencyDto constituencyDto = new ConstituencyDto();
        constituencyDto.setId(constituencyId);
        candidateDto.setConstituencyDto(constituencyDto);

        Candidate existingCandidate = new Candidate();
        PoliticalParty existingPoliticalParty = new PoliticalParty();
        Constituency existingConstituency = new Constituency();

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(existingCandidate));
        when(politicalPartyRepository.findById(politicalPartyId)).thenReturn(Optional.of(existingPoliticalParty));
        when(constituencyRepository.findById(constituencyId)).thenReturn(Optional.of(existingConstituency));

        // When
        CandidateDto result = candidateService.updateCandidate(candidateDto);

        // Then
        verify(candidateRepository, times(1)).save(any(Candidate.class));
        assertNotNull(result);
        assertEquals(candidateDto, result);
        assertEquals(candidateDto.getId(), 1L);
        assertEquals(candidateDto.getConstituencyDto().getId(), 3L);
        assertEquals(candidateDto.getPoliticalPartyDto().getId(), 2L);
    }

    @Test
    void shouldGetAllCandidates() {
        // given
        Candidate candidate1 = Candidate.builder().id(1L).name("name_1").politicalParty(PoliticalParty.builder().id(1L).build()).build();
        Candidate candidate2 = Candidate.builder().id(2L).name("name_2").build();
        List<Candidate> all = Arrays.asList(candidate1, candidate2);

        when(candidateRepository.findAll()).thenReturn(all);

        // when
        List<CandidateDto> results = candidateService.getAllCandidates();

        // then
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(results.get(0).getId(), 1L);
        assertEquals(results.get(1).getId(), 2L);
        assertEquals(results.get(0).getName(), "name_1");
        assertEquals(results.get(1).getName(), "name_2");
        assertEquals(results.get(0).getPoliticalPartyDto().getId(), 1L);
    }

    @Test
    void shouldGetCandidateByIdExists() {
        // given
        Long id = 1L;
        Candidate candidate = new Candidate();
        when(candidateRepository.findById(id)).thenReturn(Optional.of(candidate));

        // when
        CandidateDto result = candidateService.getCandidateById(id);

        // then
        assertNotNull(result);
        verify(candidateRepository, times(1)).findById(id);
    }

    @Test
    void shouldGetCandidatesForGivenVoter() {
        // given
        Constituency constituency1 = Constituency.builder().id(1L).build();
        Constituency constituency2 = Constituency.builder().id(2L).build();

        Candidate candidate1 = Candidate.builder().id(1L).constituency(constituency1).build();
        Candidate candidate2 = Candidate.builder().id(2L).constituency(constituency2).build();
        Candidate candidate3 = Candidate.builder().id(3L).constituency(constituency1).build();

        VoterDto voterDto = VoterDto.builder().constituencyDto(ConstituencyMapper.toDto(constituency1)).build();

        List<Candidate> allCandidates = Arrays.asList(candidate1, candidate2, candidate3);
        when(candidateRepository.findAll()).thenReturn(allCandidates);

        // when
        List<CandidateDto> result = candidateService.getCandidateForVoter(voterDto);

        // then
        assertNotNull(result);
        assertEquals(2, result.size()); // should only return candidates from constituency1
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(candidate1.getId())));
        assertTrue(result.stream().anyMatch(c -> c.getId().equals(candidate3.getId())));
        assertFalse(result.stream().anyMatch(c -> c.getId().equals(candidate2.getId())));
    }
}