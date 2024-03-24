package se.allfader.geneseq.domain.user;

public interface User {
    default boolean canAddNewSequence() {
        return false;
    }
    default boolean canListSequences() {
        return false;
    }
}
