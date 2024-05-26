package controller;


import dto.DipendenteDto;
import exception.BadRequestException;
import exception.DipendenteNonTrovatoException;
import model.Dipendente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.DipendenteService;

import java.io.IOException;
import java.util.Optional;

@RestController
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("api/dipendenti")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveDipendente(@RequestBody @Validated DipendenteDto dipendenteDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors()
                    .stream().map(e -> e.getDefaultMessage()).reduce("", (s, a) -> s + a));
        }
        return dipendenteService.saveDipendente(dipendenteDto);
    }

    @GetMapping("api/dipendenti")
    public Page<Dipendente> getAutori(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sortBy) {
        return dipendenteService.getAllDipendenti(page, size, sortBy);
    }

    @GetMapping("api/dipendenti/{id}")
    public Dipendente getDipendenteById(@PathVariable int id) {
        Optional<Dipendente> dipendenteOptional = dipendenteService.getDipendenteById(id);
        if (dipendenteOptional.isPresent()) {
            return dipendenteOptional.get();
        } else {
            throw new DipendenteNonTrovatoException("Dipendente non presente nel database.");
        }
    }

    @PutMapping("/api/dipendenti/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Dipendente updateDipendente(@PathVariable int id, @RequestBody @Validated DipendenteDto dipendenteDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).reduce("", ((s, s2) -> s + s2)));
        }

        return dipendenteService.updateDipendente(id, dipendenteDto);
    }

    @DeleteMapping("/api/dipendenti/{id}")
    public String deleteDipendente(@PathVariable int id) {
        return dipendenteService.deleteDipendente(id);
    }

    @PatchMapping("/api/dipendenti/{id}")
    public String patchImmagineProfiloDipendente(@PathVariable int id, @RequestBody MultipartFile immagineProfilo) throws IOException {
        return dipendenteService.patchAvatar(id, immagineProfilo);
    }

    @PatchMapping("/api/dipendenti-laptop/{dipendenteId}")
    public String patchLaptopDipendente(@PathVariable int dipendenteId, @RequestParam int laptopId) {
        return dipendenteService.patchLaptopDipendente(dipendenteId, laptopId);
    }

    @PatchMapping("/api/dipendenti-smartphone/{dipendenteId}")
    public String patchSmartphoneDipendente(@PathVariable int dipendenteId, @RequestParam int smartphoneId) {
        return dipendenteService.patchSmartphoneDipendente(dipendenteId, smartphoneId);
    }
}
