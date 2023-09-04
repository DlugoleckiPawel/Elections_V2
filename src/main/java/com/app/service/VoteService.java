package com.app.service;

import com.app.dto.VoteDto;
import com.app.dto.VoterDto;
import com.app.exception.VoteAlreadyExistsException;
import com.app.mapper.CandidateMapper;
import com.app.mapper.VoteMapper;
import com.app.mapper.VoterMapper;
import com.app.model.Candidate;
import com.app.model.Vote;
import com.app.repository.VoteRepository;
import com.app.repository.VoterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VoteService {

    private final VoteRepository voteRepository;
    private final CandidateService candidateService;
    private final VoterService voterService;
    private final VoterRepository voterRepository;

    public VoteDto addVote (Long candidateId, Long voterId) {
        VoterDto voterDto = voterService.getVoterById(voterId);

        if (Boolean.TRUE.equals(voterDto.isHasVoted())) {
            throw new VoteAlreadyExistsException();
        }

        Vote vote = voteRepository
                .findByCandidateId(candidateId)
                .orElse(Vote.builder().candidate(new Candidate()).votes(0).build());

        if (vote.getCandidate().getId() == null) {
            Candidate candidate = CandidateMapper.toEntity(candidateService.getCandidateById(candidateId));
            vote.setCandidate(candidate);
        }

        vote.setVotes(vote.getVotes() + 1);
        voterDto.setHasVoted(true);
        voterRepository.save(VoterMapper.toEntity(voterDto));


        Vote savedVote = voteRepository.save(vote);

        return VoteMapper.toDto(savedVote);
    }
}
