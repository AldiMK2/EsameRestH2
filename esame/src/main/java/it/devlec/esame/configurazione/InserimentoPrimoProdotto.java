package it.devlec.esame.configurazione;

import it.devlec.esame.model.Prodotto;
import it.devlec.esame.repository.ProdottoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
    public class InserimentoPrimoProdotto {
        private static final Logger log = LoggerFactory.getLogger(InserimentoPrimoProdotto.class);
        @Bean
        CommandLineRunner initDatabase(ProdottoRepository repository) {
            return args -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                log.info("Preloading " +  repository.save(new Prodotto("Pasta", dateFormat.parse("25-02-2002"),2.00f)));
            };
        }
    }

