package se.allfader.geneseq.domain.sequence;

import se.allfader.geneseq.domain.primitives.BasePairSequence;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record Sequence(UUID id, BasePairSequence basePairSequence) {

    public Sequence {
        Objects.requireNonNull(id, "Sequence id must not be null");
        Objects.requireNonNull(basePairSequence, "base pairs for a sequence must not be null");
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

    public SequenceHash hash(){
        return SequenceHash.from(basePairSequence.sequence());
    }
}
