package se.allfader.geneseq.model.sequence;

import se.allfader.geneseq.model.core.BasePairSequence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.function.Predicate.not;

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
