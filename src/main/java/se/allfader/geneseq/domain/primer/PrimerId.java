package se.allfader.geneseq.domain.primer;

import java.util.UUID;

public class PrimerId {
    private final UUID value;

    public PrimerId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        this.value = value;
    }

    public UUID getValue() {
        return this.value;
    }
}
