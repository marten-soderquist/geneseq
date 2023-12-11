package se.allfader.geneseq.domain.sequence;

import se.allfader.geneseq.domain.core.BasePairSequence;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Sequence {
    private final BasePairSequence basePairSequence;

    public Sequence(BasePairSequence basePairSequence) {
        this.basePairSequence = basePairSequence;
    }

    public BasePairSequence basePairSequence() {
        return basePairSequence;
    }

    public String subSequenceAtPosition(int position, int length) {
        return IntStream.range(position - 1, position + length - 1)
                .mapToObj(operand -> {
                    try {
                        return String.valueOf(basePairSequence.sequence().charAt(operand));
                    } catch (Exception e) {
                        return ".";
                    }
                })
                .collect(Collectors.joining());
    }
}
