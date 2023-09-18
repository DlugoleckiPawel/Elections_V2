package com.app.service;
import com.app.dto.PoliticalPartyDto;
import com.app.mapper.PoliticalPartyMapper;
import com.app.model.PoliticalParty;
import com.app.repository.PoliticalPartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PoliticalPartyService {
    private final PoliticalPartyRepository politicalPartyRepository;

    // DODAWANIE PARTII POLITYCZNEJ
    public PoliticalPartyDto createPoliticalParty(PoliticalPartyDto politicalPartyDto) {
        if (politicalPartyDto == null) {
            return null;
        }
        return PoliticalPartyMapper.toDto(
                politicalPartyRepository.save(
                        PoliticalPartyMapper.toEntity(politicalPartyDto)
                ));
    }
    // AKTUALIZACJA PARTII POLITYCZNEJ
    public PoliticalPartyDto updatePoliticalParty(PoliticalPartyDto politicalPartyDto) {
        if (politicalPartyDto == null || !politicalPartyRepository.existsById(politicalPartyDto.getId())) {
            throw new NullPointerException("Political Party is null");
        }
        PoliticalParty politicalParty = politicalPartyRepository
                .findById(politicalPartyDto.getId())
                .orElseThrow();
        politicalParty.setName(politicalParty.getName() == null ? politicalParty.getName() : politicalPartyDto.getName());
        politicalPartyRepository.save(politicalParty);
        return politicalPartyDto;
    }
    // POBIERANIE PARTII POLITYCZNYCH
    public List<PoliticalPartyDto> getAllParties() {
        final List<PoliticalParty> all = politicalPartyRepository.findAll();
        return PoliticalPartyMapper.toListDto(all);
    }
    // POBIERANIE PARTII POLITYCZNEJ PO ID
    public PoliticalPartyDto getPoliticalPartyById(Long id) {
        Optional<PoliticalPartyDto> politicalParty = politicalPartyRepository.findById(id).map(PoliticalPartyMapper::toDto);
        if (politicalParty.isPresent()) {
            return politicalParty.get();
        } else {
            throw new RuntimeException(" Political Party not found :: " + id);
        }
    }
    // USUWANIE PARTII POLITYCZNEJ
    public void deletePoliticalPartyById(Long id) {
        politicalPartyRepository.deleteById(id);
    }
}