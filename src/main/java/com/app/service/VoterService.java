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

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VoterService {
    private final VoterRepository voterRepository;
    private final ConstituencyRepository constituencyRepository;

    private final ConstituencyMapper constituencyMapper;

    // DODAWANIE GŁOSUJĄCEGO
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
}
