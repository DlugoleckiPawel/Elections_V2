package com.app.service;

import com.app.dto.ConstituencyDto;
import com.app.dto.VoterDto;
import com.app.mapper.ConstituencyMapper;
import com.app.mapper.VoterMapper;
import com.app.model.Voter;
import com.app.repository.ConstituencyRepository;
import com.app.repository.VoterRepository;
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
public class VoterService {
    private final VoterRepository voterRepository;
    private final ConstituencyRepository constituencyRepository;

    // DODAWANIE WYBORCY
    public VoterDto createVoter(VoterDto voterDto) {
        if (voterDto == null) {
            return null;
        }
        ConstituencyDto constituencyDto = ConstituencyMapper.toDto(constituencyRepository
                .findById(voterDto.getConstituencyDto().getId())
                .orElseThrow());

        Voter voter = VoterMapper.toEntity(voterDto);
        voter.setConstituency(ConstituencyMapper.toEntity(constituencyDto));

        VoterDto voterDtoToSave = VoterMapper.toDto(voterRepository.save(voter));
        log.info("Dodano nowego wyborcę: " + voterDtoToSave.getId() + " " + voterDtoToSave.getAge() + " " +
                voterDtoToSave.getPesel() + " " + voterDtoToSave.getConstituencyDto().getName());

        return voterDtoToSave;
    }

    // POBIERANIE WYBORCY PO ID
    public VoterDto getVoterById(Long id) {
        Optional<VoterDto> voter = voterRepository.findById(id).map(VoterMapper::toDto);
        if (voter.isPresent()) {
            return voter.get();
        } else {
            throw new RuntimeException(" Voter not found :: " + id);
        }
    }

    // POBIERANIE WYBORCÓW
    public List<VoterDto> getAllVoters() {
        final List<Voter> all = voterRepository.findAll();
        return VoterMapper.toListDto(all);
    }
}
