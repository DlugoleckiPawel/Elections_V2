package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteDto {
    private Long id;
    private CandidateDto candidateDto;
    private VoterDto voterDto;
    private Integer votes = 0;
}
