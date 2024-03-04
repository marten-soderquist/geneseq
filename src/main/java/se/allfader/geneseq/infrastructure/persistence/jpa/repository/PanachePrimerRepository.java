package se.allfader.geneseq.infrastructure.persistence.jpa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import se.allfader.geneseq.domain.primer.Primer;
import se.allfader.geneseq.domain.primer.PrimerId;
import se.allfader.geneseq.application.repository.PrimerRepository;
import se.allfader.geneseq.domain.primer.PrimerName;
import se.allfader.geneseq.domain.primitives.BasePairSequence;
import se.allfader.geneseq.infrastructure.persistence.jpa.entities.BasePairSequenceEntity;
import se.allfader.geneseq.infrastructure.persistence.jpa.entities.PrimerEntity;
import se.allfader.geneseq.infrastructure.persistence.util.Hash;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@ApplicationScoped
public class PanachePrimerRepository implements PrimerRepository {
    @ApplicationScoped
    public static class PrimerEntityPanacheRepository implements PanacheRepositoryBase<PrimerEntity, UUID> {
    }

    private final PrimerEntityPanacheRepository jpaRepo;

    public PanachePrimerRepository(PrimerEntityPanacheRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public void addPrimer(Primer primer) {
        Optional.ofNullable(primer).map(toEntity).orElseThrow(RuntimeException::new).persist();
    }

    @Override
    public Optional<Primer> findById(PrimerId id) {
        return Optional.ofNullable(jpaRepo.findById(id.getValue())).map(toDomain);
    }

    Function<Primer, PrimerEntity> toEntity = primer -> new PrimerEntity(
            primer.id().getValue(),
            primer.name().toString(),
            primer.basePairSequence().isRNA(),
            Hash.fromString(primer.basePairSequence().sequence()).getHashValue(),
            new BasePairSequenceEntity(primer.basePairSequence().id(), primer.basePairSequence().sequence())
    );

    Function<PrimerEntity, Primer> toDomain = primerEntity -> new Primer(
            new PrimerId(primerEntity.getId()),
            new PrimerName(primerEntity.getName()),
            new BasePairSequence(primerEntity.getBasePairSequence().getId(), primerEntity.getBasePairSequence().getSequence())
    );


}
