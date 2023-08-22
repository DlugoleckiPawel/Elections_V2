package com.app.controller;

import com.app.dto.ConstituencyDto;
import com.app.service.ConstituencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/constituencies")
public class ConstituencyController {
    private final ConstituencyService constituencyService;

    /**
     * Add
     */
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("constituency", new ConstituencyDto());
        return "constituencies/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute ConstituencyDto constituencyDto, Model model) {
        model.addAttribute("constituency", new ConstituencyDto());
        constituencyService.createConstituency(constituencyDto);
        return "redirect:/constituencies/add";
    }

    /**
     * Update
     */
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("constituency", constituencyService.getConstituencyById(id));
        return "constituencies/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute ConstituencyDto constituencyDto, Model model) {
        model.addAttribute("constituency", new ConstituencyDto());
        constituencyService.updateConstituency(constituencyDto);
        return "redirect:/constituencies";
    }

    /**
     * getAll
     */
    @GetMapping
    public String getAllConstituencies(Model model) {
        List<ConstituencyDto> constituencies = constituencyService.getAllConstituencies();
        model.addAttribute("constituencies", constituencies);
        return "constituencies/getAll";
    }

    /**
     * delete
     */
    @GetMapping("/delete/{id}")
    public String deleteConstituencyById(@PathVariable(value = "id") Long id) {
        constituencyService.deleteConstituencyById(id);
        return "redirect:/constituencies";
    }
}
