package service;

import com.cloudinary.Cloudinary;
import dto.DipendenteDto;
import exception.DipendenteNonTrovatoException;
import model.Dipendente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import repository.DipendenteRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Service
public class DipendenteService {
    @Autowired
    private DipendenteRepository dipendenteRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public Page<Dipendente> getAllDipendenti(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return dipendenteRepository.findAll(pageable);
    }

    public Optional<Dipendente> getDipendenteById(Integer id) {
        return dipendenteRepository.findById(id);
    }

    public String saveDipendente(DipendenteDto dipendenteDto) {
        Dipendente dipendente = new Dipendente();
        dipendente.setEmail(dipendenteDto.getEmail());
        dipendente.setNome(dipendenteDto.getNome());
        dipendente.setCognome(dipendenteDto.getCognome());
        dipendente.setUsername(dipendenteDto.getUsername());
        dipendenteRepository.save(dipendente);
        sendMail(dipendente.getEmail());
        return "Nuovo Dipendente creato con id: " + dipendente.getId();
    }

    private void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Device Manager Signup");
        message.setText("Registrazione effettuata!");

        javaMailSender.send(message);
    }

    public Dipendente updateDipendente(Integer id, DipendenteDto dipendenteDto) {
        Optional<Dipendente> dipendenteOptional = getDipendenteById(id);

        if (dipendenteOptional.isPresent()) {
            Dipendente dipendente = dipendenteOptional.get();
            dipendente.setEmail(dipendenteDto.getEmail());
            dipendente.setNome(dipendenteDto.getNome());
            dipendente.setCognome(dipendenteDto.getCognome());
            dipendente.setUsername(dipendenteDto.getUsername());
            return dipendenteRepository.save(dipendente);
        } else {
            throw new DipendenteNonTrovatoException("Dipendente id: " + id + " non presente nel database.");
        }
    }

    public String deleteDipendente(Integer id) {
        Optional<Dipendente> dipendenteOptional = getDipendenteById(id);

        if(dipendenteOptional.isPresent()) {
            dipendenteRepository.delete(dipendenteOptional.get());
            return "Il Dipendente id: " + id + " è stato correttamente eliminato.";
        } else {
            throw new DipendenteNonTrovatoException("Dipendente id: " + id + " non presente nel database.");
        }
    }

    public String patchAvatar(Integer id, MultipartFile avatar) throws IOException {
        Optional<Dipendente> dipendenteOptional = getDipendenteById(id);

        if (dipendenteOptional.isPresent()) {
            String url = (String) cloudinary.uploader().upload(avatar.getBytes(),
                    Collections.emptyMap()).get("url");
            Dipendente dipendente = dipendenteOptional.get();
            dipendente.setAvatar(url);
            dipendenteRepository.save(dipendente);
            return "Dipendente id: " + id + " - Upload dell'avatar completato.";
        } else {
            throw new DipendenteNonTrovatoException("Dipendente id: " + id + " non presente nel database.");
        }
    }
}
