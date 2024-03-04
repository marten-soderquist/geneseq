package se.allfader.geneseq.infrastructure.persistence.jpa.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "primer")
public class PrimerEntity extends PanacheEntityBase {
    public UUID getId() {
        return id;
    }

    @Id
    @Column(name = "id", updatable = false)
    private UUID id;

    public String getName() {
        return name;
    }

    @Column(name = "name", unique = true)
    private String name;

    @Column (name = "is_rna")
    private boolean isRna;

    @Column(name = "hash")
    private String hash;

    public BasePairSequenceEntity getBasePairSequence() {
        return basePairSequence;
    }

    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "base_pair_sequence_id")
    private BasePairSequenceEntity basePairSequence;

    public PrimerEntity() {
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PrimerEntity(UUID id, String name, boolean isRna, String hash, BasePairSequenceEntity basePairSequence) {
        this.id = id;
        this.name = name;
        this.isRna = isRna;
        this.hash = hash;
        this.basePairSequence = basePairSequence;
    }
}
