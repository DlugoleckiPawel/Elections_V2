package com.app.controller;
import com.app.dto.PoliticalPartyDto;
import com.app.service.PoliticalPartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/parties")
public class PoliticalPartyController {
    private final PoliticalPartyService politicalPartyService;
    /**
     * Add
     */
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("politicalParty", new PoliticalPartyDto());
        return "parties/add";
    }
    @PostMapping("/add")
    public String add(@ModelAttribute PoliticalPartyDto politicalPartyDto, Model model) {
        model.addAttribute("politicalParty", new PoliticalPartyDto());
        politicalPartyService.createPoliticalParty(politicalPartyDto);
        return "redirect:/parties/add";
    }
    /**
     * Update
     */
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("politicalParty", politicalPartyService.getPoliticalPartyById(id));
        return "parties/update";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute PoliticalPartyDto politicalPartyDto, Model model) {
        model.addAttribute("politicalParty", new PoliticalPartyDto());
        politicalPartyService.updatePoliticalParty(politicalPartyDto);
        return "redirect:/parties";
    }
    /**
     * getAll
     */
    @GetMapping
    public String getAllPoliticalParties(Model model) {
        List<PoliticalPartyDto> politicalParties = politicalPartyService.getAllParties();
        model.addAttribute("politicalParties", politicalParties);
        return "parties/getAll";
    }
    /**
     * delete
     */
    @GetMapping("/delete/{id}")
    public String deletePoliticalPartyById(@PathVariable(value = "id") Long id) {
        politicalPartyService.deletePoliticalPartyById(id);
        return "parties/getAll";
    }
}