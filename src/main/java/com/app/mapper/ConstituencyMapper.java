package com.app.mapper;

import com.app.dto.ConstituencyDto;
import com.app.model.Constituency;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class ConstituencyMapper {

    public static ConstituencyDto toDto(Constituency constituency) {
        return constituency == null ? null : ConstituencyDto
                .builder()
                .id(constituency.getId())
                .name(constituency.getName())
                .build();
    }

    public static Constituency toEntity(ConstituencyDto constituencyDto) {
        return constituencyDto == null ? null : Constituency
                .builder()
                .id(constituencyDto.getId())
                .name(constituencyDto.getName())
                .candidates(new HashSet<>())
                .voters(new HashSet<>())
                .build();
    }

    public static List<ConstituencyDto> toListDto(Collection<Constituency> constituencies) {
        if (constituencies == null) {
            return null;
        }
        List<ConstituencyDto> list = new ArrayList<>(constituencies.size());
        for (Constituency constituency : constituencies) {
            list.add(toDto(constituency));
        }
        return list;
    }
}
