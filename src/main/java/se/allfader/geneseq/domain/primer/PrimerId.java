package se.allfader.geneseq.domain.primer;

import java.util.Objects;
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

    public String asString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrimerId primerId = (PrimerId) o;
        return value.equals(primerId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
