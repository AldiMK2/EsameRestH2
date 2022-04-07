package it.devlec.esame.controller;

import it.devlec.esame.avviso.ProdottoNonTrovato;
import it.devlec.esame.model.Prodotto;
import it.devlec.esame.repository.ProdottoRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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



    private static final Logger logger = LogManager.getLogger(ProdottoRestController.class);
    private String[] INTESTAZIONE = {"Prodotto", "Prezzo"};
    private Map<String, String> MIEI_AUTORI = new HashMap<>() {
        {
            put("Pasta", "2.00");
            put("Pesto", "3.00");
            put("Ragù", "2.50");
        }
    };
    @PostMapping("/prodotti/csv")
    public ResponseEntity<String> leggiCSV(@RequestParam(name = "file")MultipartFile file) {
        Reader in = null;
        try {
            in = new InputStreamReader(file.getInputStream());
            // Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder().setSkipHeaderRecord(true)
                    .setHeader(INTESTAZIONE).build().parse(in);
            for (CSVRecord record : records) {
                String prodotto = record.get(INTESTAZIONE[0]);
                logger.info("Prodotto: " + prodotto);
                String prezzo = record.get(1);
                logger.warn("Prezzo: " + prezzo);
            }
        } catch (IOException e) {
            logger.error("Si è verificato un errore", e);
        }
        return ResponseEntity.ok("Csv caricato");
    }
}