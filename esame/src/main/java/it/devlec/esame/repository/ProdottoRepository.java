package it.devlec.esame.repository;

import it.devlec.esame.model.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {
    List<Prodotto> findByDataDiAcquistoBetween(Date datada, Date dataa);
    List<Prodotto> findByPrezzoBetween(float min, float max);
}
