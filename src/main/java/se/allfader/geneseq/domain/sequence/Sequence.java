package se.allfader.geneseq.domain.sequence;

import se.allfader.geneseq.domain.primitives.BasePairSequence;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.function.Predicate.not;

public record Sequence(UUID id, String name, BasePairSequence basePairSequence) {

    public Sequence {
        Objects.requireNonNull(id, "Sequence id must not be null");
        name = Optional.ofNullable(name).filter(not(String::isBlank)).orElseThrow(() -> new IllegalArgumentException("expected sequence to have a name"));
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
