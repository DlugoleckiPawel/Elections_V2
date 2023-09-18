package com.app.service;

import com.app.dto.CandidateDto;
import com.app.dto.ConstituencyDto;
import com.app.dto.PoliticalPartyDto;
import com.app.dto.VoterDto;
import com.app.mapper.CandidateMapper;
import com.app.mapper.ConstituencyMapper;
import com.app.mapper.PoliticalPartyMapper;
import com.app.model.Candidate;
import com.app.model.Constituency;
import com.app.model.PoliticalParty;
import com.app.repository.CandidateRepository;
import com.app.repository.ConstituencyRepository;
import com.app.repository.PoliticalPartyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final ConstituencyRepository constituencyRepository;
    private final PoliticalPartyRepository politicalPartyRepository;

    // DODAWANIE KANDYDATA
    public CandidateDto createCandidate(CandidateDto candidateDto) {
        if (candidateDto == null) {
            return null;
        }
        ConstituencyDto constituencyDto = ConstituencyMapper.toDto(constituencyRepository
                .findById(candidateDto.getConstituencyDto().getId())
                .orElseThrow());

        PoliticalPartyDto politicalPartyDto = PoliticalPartyMapper.toDto(politicalPartyRepository
                .findById(candidateDto.getPoliticalPartyDto().getId())
                .orElseThrow());

        Candidate candidate = CandidateMapper.toEntity(candidateDto);
        candidate.setConstituency(ConstituencyMapper.toEntity(constituencyDto));
        candidate.setPoliticalParty(PoliticalPartyMapper.toEntity(politicalPartyDto));

        return CandidateMapper.toDto(candidateRepository.save(candidate));
    }

    // AKTUALIZACJA KANDYDATA
    public CandidateDto updateCandidate(CandidateDto candidateDto) {
        if (candidateDto == null) {
            throw new NullPointerException("Candidate is null");
        }

        PoliticalParty politicalParty = politicalPartyRepository
                .findById(candidateDto.getPoliticalPartyDto().getId())
                .orElseThrow();

        Constituency constituency = constituencyRepository
                .findById(candidateDto.getConstituencyDto().getId())
                .orElseThrow();

        Candidate candidate = candidateRepository
                .findById(candidateDto.getId())
                .orElseThrow();

        candidate.setConstituency(constituency);
        candidate.setPoliticalParty(politicalParty);
        candidate.setName(candidate.getName() == null ? candidate.getName() : candidateDto.getName());
        candidate.setSurname(candidate.getSurname() == null ? candidate.getSurname() : candidateDto.getSurname());
        candidate.setBirthday(candidate.getBirthday() == null ? candidate.getBirthday() : candidateDto.getBirthday());
        candidate.setGender(candidate.getGender() == null ? candidate.getGender() : candidateDto.getGender());
        candidateRepository.save(candidate);

        return candidateDto;
    }

    // POBIERANIE KANDYDATÓW
    public List<CandidateDto> getAllCandidates() {
        final List<Candidate> all = candidateRepository.findAll();

        return CandidateMapper.toListDto(all);
    }


    // POBIERANIE KANDYDATA PO ID
    public CandidateDto getCandidateById(Long id) {
        Optional<CandidateDto> candidate = candidateRepository.findById(id).map(CandidateMapper::toDto);
        if (candidate.isPresent()) {
            return candidate.get();
        } else {
            throw new RuntimeException(" Candidate not found :: " + id);
        }
    }

    // USUWANIE KANDYDATA
    public void deleteCandidateById(Long id) {
        candidateRepository.deleteById(id);
    }

    public List<CandidateDto> getCandidateForVoter(VoterDto voterDto) {
        Long constituencyId = voterDto.getConstituencyDto().getId();

        return getAllCandidates()
                .stream()
                .filter(candidate -> candidate.getConstituencyDto().getId().equals(constituencyId))
                .toList();
    }
}
