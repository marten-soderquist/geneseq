package se.allfader.geneseq.infrastructure.persistence.jpa.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import se.allfader.geneseq.domain.primitives.BasePairSequence;

import java.util.UUID;
import java.util.function.Function;

@Entity
@Table(name = "base_pair_sequence")
public class BasePairSequenceEntity extends PanacheEntityBase {


    public UUID getId() {
        return id;
    }

    public String getSequence() {
        return sequence;
    }

    @Id
    private UUID id;
    @Column(name = "basepairs", nullable = false)
    private String sequence;

    public BasePairSequenceEntity(UUID id, String sequence) {
        this.id = id;
        this.sequence = sequence;
    }

    public BasePairSequenceEntity() {
    }
}
