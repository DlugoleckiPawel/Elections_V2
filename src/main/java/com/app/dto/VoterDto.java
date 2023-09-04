package com.app.dto;

import com.app.model.Education;
import com.app.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoterDto {
    private Long id;
    private String pesel;
    private Integer age;
    private Education education;
    private Gender gender;
    private ConstituencyDto constituencyDto;
    private boolean hasVoted = false;
}
