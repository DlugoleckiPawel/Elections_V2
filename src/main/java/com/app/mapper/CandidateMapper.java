package com.app.mapper;

import com.app.dto.CandidateDto;
import com.app.model.Candidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateMapper {
    private static ConstituencyMapper constituencyMapper = new ConstituencyMapper();
    private static PoliticalPartyMapper politicalPartyMapper = new PoliticalPartyMapper();
    public static CandidateDto toDto(Candidate candidate) {
        return candidate == null ? null : CandidateDto
                .builder()
                .id(candidate.getId())
                .name(candidate.getName())
                .surname(candidate.getSurname())
                .birthday(candidate.getBirthday())
                .gender(candidate.getGender())
                .constituencyDto(candidate.getConstituency() == null ? null : constituencyMapper.toDto(candidate.getConstituency()))
                .politicalPartyDto(candidate.getPoliticalParty() == null ? null : politicalPartyMapper.toDto(candidate.politicalParty))
                .build();
    }

    public static Candidate toEntity(CandidateDto candidateDto) {
        return candidateDto == null ? null : Candidate
                .builder()
                .id(candidateDto.getId())
                .name(candidateDto.getName())
                .surname(candidateDto.getSurname())
                .birthday(candidateDto.getBirthday())
                .gender(candidateDto.getGender())
                .constituency(candidateDto.getConstituencyDto() == null ? null : constituencyMapper.toEntity(candidateDto.getConstituencyDto()))
                .politicalParty(candidateDto.getPoliticalPartyDto() == null ? null : politicalPartyMapper.toEntity(candidateDto.getPoliticalPartyDto()))
                .build();
    }

    public List<CandidateDto> toListDto(Collection<Candidate> candidates) {
        if (candidates == null) {
            return null;
        }
        List<CandidateDto> list = new ArrayList<>(candidates.size());
        for (Candidate candidate : candidates) {
            list.add(toDto(candidate));
        }
        return list;
    }
}
