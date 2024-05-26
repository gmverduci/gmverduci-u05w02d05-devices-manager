package controller;


import dto.TabletDto;
import enums.StatoDispositivo;
import exception.BadRequestException;
import exception.DispositivoNonTrovatoException;
import model.Tablet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.TabletService;

import java.util.Optional;

@RestController
@RequestMapping("/api/tablet")
public class TabletController {

    @Autowired
    private TabletService tabletService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String saveTablet(@RequestBody @Validated TabletDto tabletDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors()
                    .stream().map(e -> e.getDefaultMessage()).reduce("", (s, a) -> s + a));
        }
        return tabletService.saveTablet(tabletDto);
    }

    @GetMapping
    public Page<Tablet> getTablets(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String sortBy) {
        return tabletService.getTablets(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Tablet getTabletById(@PathVariable int id) {
        Optional<Tablet> tabletOptional = tabletService.getTabletById(id);
        if (tabletOptional.isPresent()) {
            return tabletOptional.get();
        } else {
            throw new DispositivoNonTrovatoException("Dispositivo non trovato");
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tablet updateTablet(@PathVariable int id, @RequestBody @Validated TabletDto tabletDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).reduce("", (s, s2) -> s + s2));
        }

        return tabletService.updateTablets(id, tabletDto);
    }

    @DeleteMapping("/{id}")
    public String deleteTablet(@PathVariable int id) {
        return tabletService.deleteSmartphone(id);
    }

    @PatchMapping("/{id}")
    public String patchStatoTablet(@PathVariable int id, @RequestParam StatoDispositivo statoDispositivo) {
        return tabletService.patchStatoSmartphone(id, statoDispositivo);
    }
}

