package se.allfader.geneseq.application.repository;

import se.allfader.geneseq.domain.sequence.Sequence;

public interface SequenceRepository {
    void save(Sequence sequence) throws DuplicateConflict, TimeoutError;

    class DuplicateConflict extends RuntimeException {
        public DuplicateConflict() {
            super("sequence already exists");
        }
    }
    class TimeoutError extends RuntimeException{}
}
