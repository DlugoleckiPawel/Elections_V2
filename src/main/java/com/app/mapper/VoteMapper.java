package com.app.mapper;

import com.app.dto.VoteDto;
import com.app.model.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteMapper {

    public static VoteDto toDto(Vote vote) {
        return vote == null ? null : VoteDto
                .builder()
                .votes(vote.getVotes())
                .candidateDto(vote.getCandidate() == null ? null : CandidateMapper.toDto(vote.getCandidate()))
                .build();
    }

    public static Vote toEntity(VoteDto voteDto) {
        return voteDto == null ? null : Vote
                .builder()
                .votes(voteDto.getVotes())
                .candidate(voteDto.getCandidateDto() == null ? null : CandidateMapper.toEntity(voteDto.getCandidateDto()))
                .build();
    }

    public List<VoteDto> toListDto(Collection<Vote> votes) {
        if (votes == null) {
            return null;
        }
        List<VoteDto> list = new ArrayList<>(votes.size());
        for (Vote vote : votes) {
            list.add(toDto(vote));
        }
        return list;
    }
}
