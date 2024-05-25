package service;

import dto.DispositivoDto;
import enums.StatoDispositivo;
import exception.DipendenteNonTrovatoException;
import exception.DispositivoNonTrovatoException;
import model.Dipendente;
import model.Dispositivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import repository.DipendenteRepository;
import repository.DispositivoRepository;

import java.util.Optional;

@Service
public class DispositivoService {
    @Autowired
    private DispositivoRepository dispositivoRepository;
    @Autowired
    private DipendenteRepository dipendenteRepository;

    public Page<Dispositivo> getAllDispositivi(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return dispositivoRepository.findAll(pageable);
    }

    public Optional<Dispositivo> getDispositivoById(Integer id) {
        return dispositivoRepository.findById(id);
    }

    public String saveDispositivo(DispositivoDto dispositivoDto) {
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setModello(dispositivoDto.getModello());
        dispositivo.setTipoDispositivo(dispositivoDto.getTipoDispositivo());
        dispositivo.setStatoDispositivo(dispositivoDto.getStatoDispositivo());

        dispositivoRepository.save(dispositivo);
        return "Nuovo Dispositivo creato con id: " + dispositivo.getId();
    }

    public Dispositivo updateDispositivo(Integer id, DispositivoDto dispositivoDto) {
        Optional<Dispositivo> dispositivoOptional = getDispositivoById(id);

        if (dispositivoOptional.isPresent()) {
            Dispositivo dispositivo = dispositivoOptional.get();
            dispositivo.setStatoDispositivo(dispositivoDto.getStatoDispositivo());
            dispositivo.setTipoDispositivo(dispositivoDto.getTipoDispositivo());
            dispositivo.setModello(dispositivoDto.getModello());

            return dispositivoRepository.save(dispositivo);
        } else {
            throw new DispositivoNonTrovatoException("Dispositivo id: " + id + " non presente nel database.");
        }
    }

    public String deleteDispositivo(Integer id) {
        Optional<Dispositivo> dispositivoOptional = getDispositivoById(id);

        if (dispositivoOptional.isPresent()) {
            dispositivoRepository.delete(dispositivoOptional.get());
            return "Dispositivo con id "+id+" cancellato con successo";
        } else {
            throw new DispositivoNonTrovatoException("Dispositivo id: " + id + " non presente nel database.");
        }
    }

    public String assegnaDispositivo(Integer idDispositivo, Integer idDipendente) {
        Optional<Dipendente> dipendenteOptional = dipendenteRepository.findById(idDipendente);
        if (dipendenteOptional.isPresent()) {
            Optional<Dispositivo> dispositivoOptional = getDispositivoById(idDispositivo);
            if (dispositivoOptional.isPresent()) {
                Dispositivo dispositivo = dispositivoOptional.get();
                dispositivo.setDipendente(dipendenteOptional.get());
                dispositivo.setStatoDispositivo(StatoDispositivo.ASSEGNATO);
                dispositivoRepository.save(dispositivo);
                return "Il dispositivo id: " + idDispositivo + " è stato assegnato al Dipendente id: " + idDipendente + ".";
            } else {
                throw new DispositivoNonTrovatoException("Dispositivo id: " + idDispositivo + " non presente nel database.");
            }
        } else {
            throw new DipendenteNonTrovatoException("Dipendente id: " + idDipendente + " non presente nel database.");
        }
    }
}
