package com.app.service;

import com.app.dto.CandidateDto;
import com.app.dto.CandidateResultDto;
import com.app.dto.VoteDto;
import com.app.mapper.VoteMapper;
import com.app.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ResultService {
    private final VoteRepository voteRepository;
    public List<CandidateResultDto> getCandidateResultsByConstituencyId(Long constituencyId) {
        List<VoteDto> votes = voteRepository.findByCandidateConstituencyId(constituencyId)
                .stream()
                .map(VoteMapper::toDto)
                .toList();

        Map<CandidateDto, Integer> candidatesVotesMap = votes
                .stream()
                .collect(Collectors.groupingBy(VoteDto::getCandidateDto,
                        Collectors.summingInt(VoteDto::getVotes)));

        int totalVotesInConstituency = candidatesVotesMap.values().stream().mapToInt(Integer::intValue).sum();

        List<CandidateResultDto> candidateResults = new java.util.ArrayList<>(candidatesVotesMap
                .entrySet()
                .stream()
                .map(entry -> CandidateResultDto.builder()
                        .candidateDto(entry.getKey())
                        .numberOfVotes(entry.getValue())
                        .numberVotesInPercent(BigDecimal.valueOf(entry.getValue())
                                .multiply(BigDecimal.valueOf(100))
                                .divide(BigDecimal.valueOf(totalVotesInConstituency), 2, RoundingMode.HALF_UP))
                        .build()).toList());

        candidateResults.sort(Comparator.comparing(CandidateResultDto::getNumberVotesInPercent).reversed());

        return candidateResults;
    }
}
