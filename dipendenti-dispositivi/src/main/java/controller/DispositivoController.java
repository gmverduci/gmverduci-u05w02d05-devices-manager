package controller;

import dto.DispositivoDto;
import exception.DispositivoNonTrovatoException;
import exception.ParamErrorException;
import model.Dispositivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.DispositivoService;

import java.util.Optional;

@RestController
public class DispositivoController {
    @Autowired
    private DispositivoService dispositivoService;

    @GetMapping("/dispositivi")
    public Page<Dispositivo> getAllDispositivi(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "id") String sortBy) {
        return dispositivoService.getAllDispositivi(page, size, sortBy);
    }

    @GetMapping("/dispositivi/{id}")
    public Dispositivo getDispositivoById(@PathVariable Integer id) {
        Optional<Dispositivo> dispositivoOptional = dispositivoService.getDispositivoById(id);
        if (dispositivoOptional.isPresent()) {
            return dispositivoOptional.get();
        } else {
            throw new DispositivoNonTrovatoException("Dispositivo id: " + id + " non presente nel database.");
        }
    }

    @PostMapping("/dispositivi")
    public String saveDispositivo(@RequestBody @Validated DispositivoDto dispositivoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ParamErrorException(bindingResult.getAllErrors().stream().
                    map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .reduce("", ((d, d2) -> d + d2)));
        }

        return dispositivoService.saveDispositivo(dispositivoDto);
    }

    @PutMapping("/dispositivi/{id}")
    public Dispositivo updateDispositivo(@PathVariable Integer id,
                                         @RequestBody @Validated DispositivoDto dispositivoDto,
                                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ParamErrorException(bindingResult.getAllErrors().stream().
                    map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .reduce("", ((d, d2) -> d + d2)));
        }

        return dispositivoService.updateDispositivo(id, dispositivoDto);
    }

    @DeleteMapping("/dispositivi/{id}")
    public String deleteDispositivo(@PathVariable Integer id) {
        return dispositivoService.deleteDispositivo(id);
    }

    @PatchMapping("/dispositivi/{id}/dipendente")
    public String patchAssegnazioneDispositivo(@RequestBody String idDip,
                                               @PathVariable Integer id) {
        return dispositivoService.assegnaDispositivo(id, Integer.parseInt(idDip));
    }
}
