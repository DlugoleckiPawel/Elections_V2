package com.app.controller;

import com.app.dto.CandidateDto;
import com.app.dto.ConstituencyDto;
import com.app.dto.PoliticalPartyDto;
import com.app.model.Gender;
import com.app.service.CandidateService;
import com.app.service.ConstituencyService;
import com.app.service.PoliticalPartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/candidates")
public class CandidateController {
    private final CandidateService candidateService;
    private final PoliticalPartyService politicalPartyService;
    private final ConstituencyService constituencyService;

    /**
     * Add
     */
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("candidate", new CandidateDto());
        model.addAttribute("constituencies", constituencyService.getAllConstituencies());
        model.addAttribute("politicalParties", politicalPartyService.getAllParties());
        model.addAttribute("genders", Gender.values());
        return "candidates/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute CandidateDto candidateDto, Model model) {
        model.addAttribute("candidate", new CandidateDto());
        model.addAttribute("constituencies", constituencyService.getAllConstituencies());
        model.addAttribute("politicalParties", politicalPartyService.getAllParties());
        model.addAttribute("genders", Gender.values());

        candidateService.createCandidate(candidateDto);
        return "redirect:/candidates/add";
    }

    /**
     * Update
     */
    @GetMapping("/update/{id}")
    public String update(@PathVariable(value = "id") Long id, Model model) {
        CandidateDto candidateDto = candidateService.getCandidateById(id);
        List<PoliticalPartyDto> parties = politicalPartyService.getAllParties();
        List<ConstituencyDto> constituencies = constituencyService.getAllConstituencies();

        model.addAttribute("candidate", candidateDto);
        model.addAttribute("politicalParties", parties);
        model.addAttribute("constituencies", constituencies);
        model.addAttribute("genders", Gender.values());
        return "candidates/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute CandidateDto candidateDto, Model model) {
        List<PoliticalPartyDto> parties = politicalPartyService.getAllParties();
        List<ConstituencyDto> constituencies = constituencyService.getAllConstituencies();
        model.addAttribute("candidate", candidateDto);
        model.addAttribute("politicalParties", parties);
        model.addAttribute("constituencies", constituencies);
        model.addAttribute("genders", Gender.values());

        candidateService.updateCandidate(candidateDto);
        return "redirect:/candidates";
    }

    @GetMapping
    public String getAllCandidates(Model model) {
        List<CandidateDto> candidates = candidateService.getAllCandidates();
        model.addAttribute("candidates", candidates);
        return "candidates/getAll";
    }

    @GetMapping("/delete/{id}")
    public String deleteCandidateById(@PathVariable(value = "id") Long id) {
        candidateService.deleteCandidateById(id);
        return "redirect:/candidates";
    }
}
