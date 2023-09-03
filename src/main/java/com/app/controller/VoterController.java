package com.app.controller;

import com.app.dto.TokenDto;
import com.app.dto.VoterDto;
import com.app.model.Education;
import com.app.model.Gender;
import com.app.service.ConstituencyService;
import com.app.service.TokenService;
import com.app.service.VoterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/voters")
public class VoterController {
    private final VoterService voterService;
    private final TokenService tokenService;
    private final ConstituencyService constituencyService;

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("voter", new VoterDto());
        model.addAttribute("constituencies", constituencyService.getAllConstituencies());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("educations", Education.values());
        return "voters/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute VoterDto voterDto, Model model) {
        VoterDto savedVoter = voterService.createVoter(voterDto);
        TokenDto tokenDto = tokenService.createTokenForVoter(savedVoter);

        model.addAttribute("token", tokenDto.getToken());
        return "voters/showToken";
    }
}
