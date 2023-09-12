package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateResultDto {
    private CandidateDto candidateDto;
    private Integer numberOfVotes;
    private BigDecimal numberVotesInPercent;
}
