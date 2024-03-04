package se.allfader.geneseq.infrastructure.persistence.sequence;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import se.allfader.geneseq.infrastructure.persistence.jpa.entities.BasePairSequenceEntity;

import java.util.UUID;

@Table(name = "sequence")
@Entity
public class SequenceEntity extends PanacheEntityBase {
    @Column(name = "hash", unique = true, updatable = false)
    byte[] hash;
//    @Column(name = "sequence")
//    String sequence;

    @OneToOne(fetch = FetchType.LAZY)
    BasePairSequenceEntity sequence;
    @Id
    @Column(name = "id")
    UUID id;

}
