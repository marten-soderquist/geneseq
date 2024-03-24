package se.allfader.geneseq.infrastructure.persistence.sequence;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import se.allfader.geneseq.application.query.sequence.StoredSequence;
import se.allfader.geneseq.infrastructure.persistence.jpa.entities.BasePairSequenceEntity;

import java.util.Optional;
import java.util.UUID;

@Table(name = "sequence")
@Entity
class SequenceEntity extends PanacheEntityBase {
    @Column(name = "hash", unique = true, updatable = false)
    byte[] hash;
    @Column(name = "name", unique = true)
    String name;

    @OneToOne(fetch = FetchType.LAZY)
    BasePairSequenceEntity sequence;
    @Id
    @Column(name = "id")
    UUID id;

    public static Optional<SequenceEntity> findByName (final String name){
        return find("name", name).firstResultOptional();
    }

    public static Optional<SequenceEntity> findByHash(final byte[] hash ){
        return find("hash", hash).firstResultOptional();
    }

    public StoredSequence readModel() {
        return new StoredSequence() {
            @Override
            public UUID id() {
                return id;
            }

            @Override
            public String name() {
                return name;
            }

            @Override
            public String basepairs() {
                return sequence.getSequence();
            }
        };
    }
}
