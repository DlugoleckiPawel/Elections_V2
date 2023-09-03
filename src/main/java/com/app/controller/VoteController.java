package com.app.controller;

import com.app.dto.VoterDto;
import com.app.exception.TokenExpiredException;
import com.app.exception.TokenNotFoundException;
import com.app.service.TokenService;
import com.app.service.VoterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/votes")
public class VoteController {
    private final VoterService voterService;
    private final TokenService tokenService;

    /**
     * Metoda get do wyświetlenia wyborców oraz możliwość ich zarejestrowania
     */

    @GetMapping("/voters")
    public String getAllVoters(Model model) {
        List<VoterDto> voters = voterService.getAllVoters();
        model.addAttribute("voters", voters);
        return "votes/voters";
    }

    /**
     * Metoda get do wyświetlenia formularza do wpisania tokenu
     */

    @GetMapping("/register/{id}")
    public String showRegistrationForm(@PathVariable Long id, Model model) {
        VoterDto voterDto = voterService.getVoterById(id);
        model.addAttribute("voter", voterDto);
        return "votes/register";
    }

    /**
     * Metoda post do przetworzenia przesłanego tokenu
     */
    @PostMapping("/register/{id}")
    public String register(@PathVariable Long id, @RequestParam String token, Model model) {
        try {
            VoterDto voterDto = voterService.getVoterById(id);
            tokenService.isTokenValidForToken(voterDto, token);
            return "votes/confirmation";

        } catch (TokenNotFoundException e) {
            model.addAttribute("error", "Podano niepoprawny token");
            model.addAttribute("voter", voterService.getVoterById(id));
            return "votes/register";
        } catch (TokenExpiredException e) {
            model.addAttribute("voter", voterService.getVoterById(id));
            return "votes/tokenExpired";
        }
    }
}
