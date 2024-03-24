package se.allfader.geneseq.application.repository;

import se.allfader.geneseq.domain.sequence.Sequence;

public interface SequenceRepository {
    /**
     * Handles persisting sequences. The implementation must ensure that a sequence name is unique as well as that
     * sequence base pair sequences are unique. Any violation of these invariants MUST throw {@link DuplicateConflict}
     * with an error message.
     * @param sequence The seqyence to save
     * @throws DuplicateConflict if any of the invariants mentioned above are violated.
     * @throws TimeoutError if the operation times out.
     */
    void save(Sequence sequence) throws DuplicateConflict, TimeoutError;

    class DuplicateConflict extends RuntimeException {
        public DuplicateConflict() {
            super("sequence already exists");
        }
    }
    class TimeoutError extends RuntimeException{}
}
