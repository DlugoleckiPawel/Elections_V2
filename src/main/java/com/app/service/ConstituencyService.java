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

    // DODAWANIE OKRĘGU WYBORCZEGO
    public ConstituencyDto createConstituency(ConstituencyDto constituencyDto) {
        if (constituencyDto == null) {
            return null;
        }
        return ConstituencyMapper.toDto(
                constituencyRepository.save(
                        ConstituencyMapper.toEntity(constituencyDto)
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

    // POBIERANIE OKRĘGÓW WYBORCZYCH
    public List<ConstituencyDto> getAllConstituencies() {
        final List<Constituency> all = constituencyRepository.findAll();

        return ConstituencyMapper.toListDto(all);
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
