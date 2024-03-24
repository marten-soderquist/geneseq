package se.allfader.geneseq.infrastructure.persistence.sequence;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import se.allfader.geneseq.application.query.sequence.StoredSequence;
import se.allfader.geneseq.application.query.sequence.StoredSequenceRepository;
import se.allfader.geneseq.application.repository.SequenceRepository;
import se.allfader.geneseq.domain.primitives.BasePairSequence;
import se.allfader.geneseq.domain.sequence.Sequence;
import se.allfader.geneseq.infrastructure.persistence.jpa.entities.BasePairSequenceEntity;

import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ApplicationScoped
public class PostgresSequenceRepository implements SequenceRepository, StoredSequenceRepository {

    Function<BasePairSequence, BasePairSequenceEntity> toEntity = bp -> new BasePairSequenceEntity(bp.id(), bp.sequence());

    Function<Sequence, SequenceEntity> toJpaEntity = d -> {
        SequenceEntity sequenceEntity = new SequenceEntity();
        sequenceEntity.hash = d.hash().value();
        sequenceEntity.id = d.id();
        sequenceEntity.name = d.name();
        sequenceEntity.sequence = Optional.ofNullable(d.basePairSequence()).map(toEntity).get();
        return sequenceEntity;
    };

    @Override
    public void save(Sequence sequence) throws DuplicateConflict {

        if(SequenceEntity.findByName(sequence.name()).isPresent()){
            throw new DuplicateConflict();
        }

        try {
            SequenceEntity sequenceEntity = Optional.ofNullable(sequence)
                    .map(toJpaEntity).orElseThrow(RuntimeException::new);

            sequenceEntity.sequence.persist();
            sequenceEntity.persistAndFlush();
        } catch (ConstraintViolationException constraintError) {
            Log.debug(constraintError.getMessage());
            throw mapPostgresError.apply(constraintError.getSQLState());
        }
    }

    Function<String, RuntimeException> mapPostgresError = string -> switch (string) {
        case "23505" -> new DuplicateConflict();
        default -> new RuntimeException("unhandled error in database communcation");
    };

    @Override
    public long count() {
        return SequenceEntity.count();
    }

    @Override
    public List<StoredSequence> pageOfSequences(int pageNumber, int pageSize) {

        PanacheQuery<SequenceEntity> query = SequenceEntity.findAll()
                .page(pageNumber, pageSize);
        return query.stream().map(SequenceEntity::readModel).toList();
    }
}
