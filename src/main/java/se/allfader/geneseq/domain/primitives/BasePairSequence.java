package se.allfader.geneseq.domain.primitives;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.function.Predicate.not;

public final class BasePairSequence {
    public static final Collection<Character> VALID_CHARACTERS = List.of('a', 't', 'c', 'g', 'u');
    private final UUID id;
    private final String sequence;
    private final boolean isRna;

    public BasePairSequence(UUID id, String sequence) {
        this.id = id;
        String cleanSequence = Optional.ofNullable(sequence)
                .map(s -> s.replace(" ", ""))
                .map(String::toLowerCase)
                .filter(not(String::isBlank))
                .orElseThrow(() -> new IllegalArgumentException("sequence is empty"));
        throwIfContainsInvalidCharacter(cleanSequence);
        this.isRna = isRNASequence(cleanSequence);
        if (isRna) {
            throwIfContainsT(cleanSequence);
        }
        this.sequence = cleanSequence;
    }

    public String reverseAntiSense() {
        String reversedBases = sequence.toLowerCase()
                .replace("a", "T")
                .replace("t", "A")
                .replace("c", "G")
                .replace("g", "C")
                .replace("u", "A")
                .toLowerCase();
        return new StringBuilder(reversedBases).reverse().toString();
    }

    private void throwIfContainsT(final String s) {
        IntStream.range(0, s.length())
                .forEach(i -> {
                    if (s.charAt(i) == 't') {
                        throw new IllegalArgumentException("RNA sequence contains t [pos=%s]".formatted(i + 1));
                    }
                });
    }

    private static void throwIfContainsInvalidCharacter(final String s) {
        IntStream.range(0, s.length())
                .forEach(i -> {
                    if (!VALID_CHARACTERS.contains(s.charAt(i))) {
                        throw new IllegalArgumentException("sequence contains invalid character %s [pos=%s]"
                                .formatted(s.charAt(i), i + 1));
                    }
                });
    }

    public boolean isRNA() {
        return isRna;
    }

    private static boolean isRNASequence(final String s) {
        return s.contains("u");
    }

    private static boolean containsOnlyValidCharacters(final String s) {
        return s.matches("[actg]]*");
    }

    public String sense() {
        return sequence;
    }

    public int numberOfBasePairs() {
        return sequence.length();
    }

    public String sequence() {
        return sequence;
    }

    public UUID id() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BasePairSequence) obj;
        return Objects.equals(this.sequence, that.sequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequence);
    }

    @Override
    public String toString() {
        return "BasePairSequence[" +
                "sequence=" + sequence + ']';
    }

}
