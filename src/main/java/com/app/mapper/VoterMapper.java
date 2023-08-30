package com.app.mapper;

import com.app.dto.VoterDto;
import com.app.model.Voter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoterMapper {
    private static final ConstituencyMapper constituencyMapper = new ConstituencyMapper();

    public static VoterDto toDto(Voter voter) {
        return voter == null ? null : VoterDto
                .builder()
                .id(voter.getId())
                .age(voter.getAge())
                .pesel(voter.getPesel())
                .education(voter.getEducation())
                .gender(voter.getGender())
                .constituencyDto(voter.getConstituency() == null ? null : constituencyMapper.toDto(voter.getConstituency()))
                .build();
    }

    public static Voter toEntity(VoterDto voterDto) {
        return voterDto == null ? null : Voter
                .builder()
                .id(voterDto.getId())
                .age(voterDto.getAge())
                .pesel(voterDto.getPesel())
                .education(voterDto.getEducation())
                .gender(voterDto.getGender())
                .constituency(voterDto.getConstituencyDto() == null ? null : constituencyMapper.toEntity(voterDto.getConstituencyDto()))
                .build();
    }

    public List<VoterDto> toListDto(Collection<Voter> voters) {
        if (voters == null) {
            return null;
        }
        List<VoterDto> list = new ArrayList<>(voters.size());
        for (Voter voter : voters) {
            list.add(toDto(voter));
        }
        return list;
    }
}
