package se.allfader.geneseq.domain.primer;

import java.util.Objects;
import java.util.Optional;

public class PrimerName {
    private final String value;
    private static final int MAX_NAME_LENGTH = 18;

    public PrimerName(final String name){
        value = validateInput(name);
    }

    private String validateInput(String name) {
        checkNotNull(name);
        String trimmedName = trimName(name);
        checkNotEmpty(trimmedName);
        checkNotTooLong(trimmedName);
        checkNotContainsInvalidCharacters(trimmedName);
        return trimmedName;
    }

    private void checkNotContainsInvalidCharacters(String trimmedName) {
        if (trimmedName.matches(".*[!=].*")) {
            throw new IllegalArgumentException("contains invalid characters");
        }
    }

    private void checkNotNull(String name) {
        Optional.ofNullable(name).orElseThrow(() -> new IllegalArgumentException("must not be null"));
    }

    private String trimName(String name) {
        return name.trim();
    }

    private void checkNotEmpty(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("must not be empty");
        }
    }

    private void checkNotTooLong(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("name must not exceed " + MAX_NAME_LENGTH + " characters");
        }
    }

    public static PrimerName fromString(final String newName){
        return new PrimerName(newName);
    }

    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrimerName that = (PrimerName) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
