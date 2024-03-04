package se.allfader.geneseq.application.repository;

import io.smallrye.mutiny.Uni;
import se.allfader.geneseq.domain.primer.Primer;
import se.allfader.geneseq.domain.primer.PrimerId;

import java.util.List;
import java.util.Optional;

public interface PrimerRepository {
    void addPrimer(Primer primer);

    Optional<Primer> findById(PrimerId id);
}
