package se.allfader.geneseq.domain.primitives;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public final class BasePairSequence {
    private final String sequence;
    private final boolean isRna;

    public BasePairSequence(String sequence) {
        throwIfContainsInvalidCharacter(sequence);
        this.isRna = isRNASequence(sequence);
        if (isRna) {
            throwIfContainsT(sequence);
        }
        this.sequence = sequence.toLowerCase();
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
        Collection<Character> validCharacters = List.of('A', 'T', 'C', 'G', 'U', 'a', 't', 'c', 'g', 'u');
        IntStream.range(0, s.length())
                .forEach(i -> {
                    if (!validCharacters.contains(s.charAt(i))) {
                        throw new IllegalArgumentException("sequence contains invalid character %s [pos=%s]"
                                .formatted(s.charAt(i), i + 1));
                    }
                });
    }

    public boolean isRNA() {
        return isRna;
    }

    private static boolean rnaSequenceDoesNotContainT(final String s) {
        return isRNASequence(s) && s.contains("t");
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
