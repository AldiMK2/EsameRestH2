package it.devlec.esame.controller;

import it.devlec.esame.avviso.ProdottoNonTrovato;
import it.devlec.esame.model.Prodotto;
import it.devlec.esame.repository.ProdottoRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.List;

@RestController
public class ProdottoRestController<logger> {
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
    ) {
        return repository.findByDataDiAcquistoBetween(datada, dataa);
    }

    @GetMapping("/prodotti/ricercaprezzo")
    public List<Prodotto> ricercaProdottoPrezzo(
            @RequestParam(name = "min") float min,
            @RequestParam(name = "max") float max
    ) {
        return repository.findByPrezzoBetween(min, max);
    }

    @PutMapping("/prodotti/{id}")
    Prodotto aggiornaProdotto(@RequestBody Prodotto prodotto, @PathVariable Long id) {
        return repository.save(prodotto);
    }

    @DeleteMapping("/prodotti/{id}")
    void eliminaProdotto(@PathVariable Long id) {
        repository.deleteById(id);
    }


    @PostMapping("/prodotti/csv")
    public ResponseEntity<String> caricaCSV(@RequestParam("file") @NotNull MultipartFile file) {
        Logger logger = LoggerFactory.getLogger(ProdottoRestController.class);
        Reader in = null;
        try {
            in = new InputStreamReader(file.getInputStream());
            //Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder().build().parse(in);
            for (CSVRecord record : records) {
                String prodotto = record.get(0);
                logger.info("Prodotto: " + prodotto);
                String prezzo = record.get(1);
                logger.warn("Prezzo: " + prezzo);
            }
        } catch (IOException e) {
            logger.error("Si è verificato un errore", e);
        }
        return ResponseEntity.ok("CSV");
    }

}