package com.app.mapper;

import com.app.dto.CandidateDto;
import com.app.model.Candidate;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CandidateMapper {
    public static CandidateDto toDto(Candidate candidate) {
        return candidate == null ? null : CandidateDto
                .builder()
                .id(candidate.getId())
                .name(candidate.getName())
                .surname(candidate.getSurname())
                .birthday(candidate.getBirthday())
                .gender(candidate.getGender())
                .constituencyDto(candidate.getConstituency() == null ? null : ConstituencyMapper.toDto(candidate.getConstituency()))
                .politicalPartyDto(candidate.getPoliticalParty() == null ? null : PoliticalPartyMapper.toDto(candidate.getPoliticalParty()))
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
                .constituency(candidateDto.getConstituencyDto() == null ? null : ConstituencyMapper.toEntity(candidateDto.getConstituencyDto()))
                .politicalParty(candidateDto.getPoliticalPartyDto() == null ? null : PoliticalPartyMapper.toEntity(candidateDto.getPoliticalPartyDto()))
                .build();
    }

    public static List<CandidateDto> toListDto(Collection<Candidate> candidates) {
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
