package service;



import dto.SmartphoneDto;
import enums.StatoDispositivo;
import exception.DispositivoNonTrovatoException;
import model.Smartphone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import repository.SmartphoneRepository;

import java.util.Optional;

@Service
public class SmartphoneService {
    @Autowired
    private SmartphoneRepository smartphoneRepository;

    public String saveSmartphone(SmartphoneDto smartphoneDto) {
        Smartphone smartphone = new Smartphone();
        smartphone.setMarca(smartphoneDto.getMarca());
        smartphone.setModello((smartphoneDto.getModello()));
        smartphone.setSchermo(smartphoneDto.getSchermo());
        smartphone.setStatoDispositivo(smartphoneDto.getStatoDispositivo());
        smartphone.setTipoSmartphone(smartphoneDto.getTipoSmartphone());

        smartphoneRepository.save(smartphone);
        return "Smartphone con ID " + smartphone.getId() + " creato con successo.";
    }

    public Page<Smartphone> getSmartphones(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return smartphoneRepository.findAll(pageable);
    }

    public Optional<Smartphone> getSmartphoneById(int id) {
        return smartphoneRepository.findById(id);
    }

    public Smartphone updateSmartphone(int id, SmartphoneDto smartphoneDto) {
        Optional<Smartphone> smartphoneOptional = getSmartphoneById(id);

        if (smartphoneOptional.isPresent()) {
            Smartphone smartphone = smartphoneOptional.get();
            smartphone.setMarca(smartphoneDto.getMarca());
            smartphone.setModello((smartphoneDto.getModello()));
            smartphone.setSchermo(smartphoneDto.getSchermo());
            smartphone.setStatoDispositivo(smartphoneDto.getStatoDispositivo());
            smartphone.setTipoSmartphone(smartphoneDto.getTipoSmartphone());

            smartphoneRepository.save(smartphone);
            return smartphone;
        } else {
            throw new DispositivoNonTrovatoException("Dispositivo id: " + id + " non presente nel database.");
        }
    }

    public String deleteSmartphone(int id) {
        Optional<Smartphone> smartphoneOptional = getSmartphoneById(id);

        if (smartphoneOptional.isPresent()) {
            smartphoneRepository.delete(smartphoneOptional.get());
            return "Smartphone id: " + id + " eliminato con successo.";
        } else {
            throw new DispositivoNonTrovatoException("Dispositivo con id " + id + " non presente nel database.");
        }
    }
    public String patchStatoSmartphone(int id, StatoDispositivo statoDispositivo) {
        Optional<Smartphone> smartphoneOptional = getSmartphoneById(id);
        if (smartphoneOptional.isPresent()) {
            Smartphone smartphone = smartphoneOptional.get();
            smartphone.setStatoDispositivo(statoDispositivo);
            smartphoneRepository.save(smartphone);
            return "Stato dello Smartphone id: " + id + " aggiornato con successo.";
        } else {
            throw new DispositivoNonTrovatoException("Dispositivo con id " + id + " non presente nel database.");
        }
    }

}
