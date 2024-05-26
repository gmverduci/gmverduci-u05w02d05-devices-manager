package service;


import dto.LaptopDto;
import enums.StatoDispositivo;
import exception.DispositivoNonTrovatoException;
import model.Laptop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import repository.LaptopRepository;

import java.util.Optional;

@Service
public class LaptopService {
    @Autowired
    private LaptopRepository laptopRepository;

    public String saveLaptop(LaptopDto laptopDto) {
        Laptop laptop = new Laptop();
        laptop.setMarca(laptopDto.getMarca());
        laptop.setModello((laptopDto.getModello()));
        laptop.setSchermo(laptopDto.getSchermo());
        laptop.setStatoDispositivo(laptopDto.getStatoDispositivo());
        laptop.setRam(laptopDto.getRam());

        laptopRepository.save(laptop);
        return "Laptop id: " + laptop.getId() + " creato con successo.";
    }

    public Page<Laptop> getLaptops(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return laptopRepository.findAll(pageable);
    }

    public Optional<Laptop> getLaptopById(int id) {
        return laptopRepository.findById(id);
    }

    public Laptop updateLaptop(int id, LaptopDto laptopDto) {
        Optional<Laptop> laptopOptional = getLaptopById(id);

        if (laptopOptional.isPresent()) {
            Laptop laptop = laptopOptional.get();
            laptop.setMarca(laptopDto.getMarca());
            laptop.setModello((laptopDto.getModello()));
            laptop.setSchermo(laptopDto.getSchermo());
            laptop.setStatoDispositivo(laptopDto.getStatoDispositivo());
            laptop.setRam(laptopDto.getRam());

            laptopRepository.save(laptop);
            return laptop;
        } else {
            throw new DispositivoNonTrovatoException("Dispositivo id: " + id + " non presente nel database.");
        }
    }

    public String deleteLaptop(int id) {
        Optional<Laptop> laptopOptional = getLaptopById(id);

        if (laptopOptional.isPresent()) {
            laptopRepository.delete(laptopOptional.get());
            return "Laptop id: " + id + " eliminato con successo.";
        } else {
            throw new DispositivoNonTrovatoException("Dispositivo id: " + id + " non trovato");
        }
    }

    public String patchStatoLaptop(int id, StatoDispositivo statoDispositivo) {
        Optional<Laptop> laptopOptional = getLaptopById(id);
        if (laptopOptional.isPresent()) {
            Laptop laptop = laptopOptional.get();
            laptop.setStatoDispositivo(statoDispositivo);
            laptopRepository.save(laptop);
            return "Stato del Laptop id: " + id + " aggiornato con successo.";
        } else {
            throw new DispositivoNonTrovatoException("Dispositivo con id: " + id + " non presente nel database.");
        }
    }

}
