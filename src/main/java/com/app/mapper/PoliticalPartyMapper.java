package com.app.mapper;
import com.app.dto.PoliticalPartyDto;
import com.app.model.PoliticalParty;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class PoliticalPartyMapper {
    public static PoliticalPartyDto toDto(PoliticalParty politicalParty) {
        return politicalParty == null ? null : PoliticalPartyDto
                .builder()
                .id(politicalParty.getId())
                .name(politicalParty.getName())
                .build();
    }
    public static PoliticalParty toEntity(PoliticalPartyDto politicalPartyDto) {
        return politicalPartyDto == null ? null : PoliticalParty
                .builder()
                .id(politicalPartyDto.getId())
                .name(politicalPartyDto.getName())
                .candidates(new HashSet<>())
                .build();
    }
    public static List<PoliticalPartyDto> toListDto(Collection<PoliticalParty> politicalParties) {
        if (politicalParties == null) {
            return null;
        }
        List<PoliticalPartyDto> list = new ArrayList<>(politicalParties.size());
        for (PoliticalParty politicalParty : politicalParties) {
            list.add(toDto(politicalParty));
        }
        return list;
    }
}