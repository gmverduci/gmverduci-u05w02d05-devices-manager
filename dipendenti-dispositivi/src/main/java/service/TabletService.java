package service;

import dto.TabletDto;
import enums.StatoDispositivo;
import exception.DispositivoNonTrovatoException;
import model.Tablet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import repository.TabletRepository;

import java.util.Optional;

@Service
public class TabletService {
    @Autowired
    private TabletRepository tabletRepository;

    public String saveTablet(TabletDto tabletDto) {
        Tablet tablet = new Tablet();
        tablet.setMarca(tabletDto.getMarca());
        tablet.setModello((tabletDto.getModello()));
        tablet.setSchermo(tabletDto.getSchermo());
        tablet.setStatoDispositivo(StatoDispositivo.valueOf(tabletDto.getStatoDispositivo()));
        tablet.setSimInternet(tabletDto.isSimInternet());

        tabletRepository.save(tablet);
        return "Tablet id:  " + tablet.getId() + " creato con successo.";
    }

    public Page<Tablet> getTablets(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return tabletRepository.findAll(pageable);
    }

    public Optional<Tablet> getTabletById(int id) {
        return tabletRepository.findById(id);
    }

    public Tablet updateTablets(int id, TabletDto tabletDto) {
        Optional<Tablet> tabletOptional = getTabletById(id);

        if (tabletOptional.isPresent()) {
            Tablet tablet = tabletOptional.get();
            tablet.setMarca(tabletDto.getMarca());
            tablet.setModello((tabletDto.getModello()));
            tablet.setSchermo(tabletDto.getSchermo());
            tablet.setStatoDispositivo(StatoDispositivo.valueOf(tabletDto.getStatoDispositivo()));
            tablet.setSimInternet(tabletDto.isSimInternet());

            tabletRepository.save(tablet);
            return tablet;
        } else {
            throw new DispositivoNonTrovatoException("Dispositivo id:  " + id + " non presente nel database");
        }
    }

    public String deleteSmartphone(int id) {
        Optional<Tablet> smartphoneOptional = getTabletById(id);

        if (smartphoneOptional.isPresent()) {
            tabletRepository.delete(smartphoneOptional.get());
            return "Tablet id: " + id + " eliminato con successo.";
        } else {
            throw new DispositivoNonTrovatoException("Dispositivo id:  " + id + " non presente nel database");
        }
    }

    public String patchStatoSmartphone(int id, StatoDispositivo statoDispositivo) {
        Optional<Tablet> tabletOptional = getTabletById(id);
        if (tabletOptional.isPresent()) {
            Tablet tablet = tabletOptional.get();
            tablet.setStatoDispositivo(statoDispositivo);
            tabletRepository.save(tablet);
            return "Stato del Tablet id: " + id + " aggiornato con successo.";
        } else {
            throw new DispositivoNonTrovatoException("Dispositivo id:  " + id + " non presente nel database");
        }
    }

}
