package it.devlec.esame.controller;

import it.devlec.esame.avviso.ProdottoNonTrovato;
import it.devlec.esame.model.Prodotto;
import it.devlec.esame.repository.ProdottoRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ProdottoRestController {
    private final ProdottoRepository repository;
    ProdottoRestController(ProdottoRepository repository) {
        this.repository = repository;
    }
    @GetMapping("/prodotti")
    List<Prodotto> tutti() {
        return repository.findAll();
    }
    @PostMapping("/prodotto")
    Prodotto nuovoProdotto(@RequestBody Prodotto nuovoProdotto) {
        return repository.save(nuovoProdotto);
    }
    @GetMapping("/prodotti/{id}")
    Prodotto singoloProdotto(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProdottoNonTrovato(id));
    }
    @GetMapping("/prodotti/ricercatradate")
    public List<Prodotto> ricercaProdottoTraDate(
            @RequestParam(name = "datada") @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date datada,
            @RequestParam(name = "dataa") @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date dataa
    ){
        return repository.findByDataDiAcquistoBetween(datada,dataa);
    }

    @GetMapping("/prodotti/ricercaranking")
    public List<Prodotto> ricercaProdottoRanking(
            @RequestParam(name = "min") float min,
            @RequestParam(name = "max") float max
    ){
        return repository.findByRankingBetween(min,max);
    }

    @PutMapping("/prodotti/{id}")
    Prodotto aggiornaProdotto(@RequestBody Prodotto prodotto, @PathVariable Long id) {
        return repository.save(prodotto);
    }

    @DeleteMapping("/prodotti/{id}")
    void eliminaProdotto(@PathVariable Long id) {
        repository.deleteById(id);
    }
}