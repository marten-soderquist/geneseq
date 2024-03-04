package se.allfader.geneseq.domain.exception;

public class DuplicatePrimerName extends IllegalArgumentException {

    private static final String ERROR_MESSAGE = "primer name %s is already used";
    public DuplicatePrimerName(final String duplicatedName) {
        super(ERROR_MESSAGE.formatted(duplicatedName));
    }
}
