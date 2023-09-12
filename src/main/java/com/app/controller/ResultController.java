package com.app.controller;

import com.app.dto.CandidateResultDto;
import com.app.dto.ConstituencyDto;
import com.app.service.ConstituencyService;
import com.app.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/results")
public class ResultController {
    private final ResultService resultService;
    private final ConstituencyService constituencyService;

    @GetMapping("/constituencies")
    public String listConstituency(Model model) {
        List<ConstituencyDto> constituencies = constituencyService.getAllConstituencies();
        model.addAttribute("constituencies", constituencies);
        return "results/constituencies";
    }

    @GetMapping("/showResults/{id}")
    public String showElectionResult(@PathVariable(value = "id") Long id, Model model) {
        List<CandidateResultDto> results = resultService.getCandidateResultsByConstituencyId(id);
        ConstituencyDto constituencyDto = constituencyService.getConstituencyById(id);
        model.addAttribute("constituency", constituencyDto);
        model.addAttribute("results", results);
        return "results/showResults";
    }
}
