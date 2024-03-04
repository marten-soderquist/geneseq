package se.allfader.geneseq.infrastructure.persistence.jpa.repository;

import se.allfader.geneseq.domain.primer.Primer;
import se.allfader.geneseq.domain.primer.PrimerId;
import se.allfader.geneseq.domain.primer.PrimerName;
import se.allfader.geneseq.domain.primitives.BasePairSequence;
import se.allfader.geneseq.infrastructure.persistence.jpa.entities.BasePairSequenceEntity;
import se.allfader.geneseq.infrastructure.persistence.util.Hash;
import se.allfader.geneseq.infrastructure.persistence.jpa.entities.PrimerEntity;

public class PrimerMapper {
    public static PrimerEntity mapFromDomain(Primer primer) {
        return new PrimerEntity(
                primer.id().getValue(),
                primer.name().toString(),
                primer.basePairSequence().isRNA(),
                Hash.fromString(primer.basePairSequence().sequence()).getHashValue(),
                new BasePairSequenceEntity(primer.basePairSequence().id(), primer.basePairSequence().sequence())
        );
    }

    public static Primer mapToDomain(final PrimerEntity primerEntity) {
        return new Primer(
                new PrimerId(primerEntity.getId()),
                new PrimerName(primerEntity.getName()),
                new BasePairSequence(primerEntity.getBasePairSequence().getId(), primerEntity.getBasePairSequence().getSequence())
                );
    }
}
