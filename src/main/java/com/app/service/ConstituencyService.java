package com.app.service;

import com.app.dto.ConstituencyDto;
import com.app.mapper.ConstituencyMapper;
import com.app.model.Constituency;
import com.app.repository.ConstituencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ConstituencyService {
    private final ConstituencyRepository constituencyRepository;
    private final ConstituencyMapper constituencyMapper;

    // DODAWANIE OKRĘGU WYBORCZEGO
    public ConstituencyDto createConstituency(ConstituencyDto constituencyDto) {
        if (constituencyDto == null) {
            return null;
        }
        return constituencyMapper.toDto(
                constituencyRepository.save(
                        constituencyMapper.toEntity(constituencyDto)
                ));
    }

    // AKTUALIZACJA OKRĘGU WYBORCZEGO
    public ConstituencyDto updateConstituency(ConstituencyDto constituencyDto) {
        if (constituencyDto == null || !constituencyRepository.existsById(constituencyDto.getId())) {
            throw new NullPointerException("Constituency is null");
        }

        Constituency constituency = constituencyRepository
                .findById(constituencyDto.getId())
                .orElseThrow();

        constituency.setName(constituency.getName() == null ? constituency.getName() : constituencyDto.getName());
        constituencyRepository.save(constituency);

        return constituencyDto;
    }

    // POBIERANIE OKRĘGU WYBORCZEGO
    public List<ConstituencyDto> getAllConstituencies() {
        final List<Constituency> all = constituencyRepository.findAll();

        return constituencyMapper.toListDto(all);
    }

    // POBIERANIE OKRĘGU WYBORCZEGO PO ID
    public ConstituencyDto getConstituencyById(Long id) {
        Optional<ConstituencyDto> constituency = constituencyRepository.findById(id).map(ConstituencyMapper::toDto);
        if (constituency.isPresent()) {
            return constituency.get();
        } else {
            throw new RuntimeException(" Constituency not found :: " + id);
        }
    }

    // USUWANIE OKRĘGU WYBORCZEGO
    public void deleteConstituencyById(Long id) {
        constituencyRepository.deleteById(id);
    }
}
