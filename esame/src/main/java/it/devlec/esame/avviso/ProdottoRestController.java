package it.devlec.esame.avviso;

import it.devlec.esame.model.Prodotto;
import it.devlec.esame.repository.ProdottoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProdottoRestController {
    private final ProdottoRepository repository;
    ProdottoRestController(ProdottoRepository repository) {
        this.repository = repository;
    }
    @GetMapping("/utenti")
    List<Prodotto> tutti() {
        return repository.findAll();
    }
    @PostMapping("/utente")
    Prodotto nuovoProdotto(@RequestBody Prodotto nuovoProdotto) {
        return repository.save(nuovoProdotto);
    }
    @GetMapping("/utenti/{id}")
    Prodotto singoloProdotto(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProdottoNonTrovato(id));
    }
    @PutMapping("/utenti/{id}")
    Prodotto aggiornaUtente(@RequestBody Prodotto prodotto, @PathVariable Long id) {
        return repository.save(prodotto);}
    @DeleteMapping("/utente/{id}")
    void eliminaProdotto(@PathVariable Long id) {
        repository.deleteById(id);
    }
}