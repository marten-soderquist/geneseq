package se.allfader.geneseq.domain.primer;

import se.allfader.geneseq.domain.primitives.BasePairSequence;

import java.util.Objects;
import java.util.Optional;

public class Primer {
    private final BasePairSequence basePairSequence;
    private final PrimerId primerId;
    private PrimerName name;

    public Primer(PrimerId primerId, PrimerName name, BasePairSequence basePairSequence) {
        this.name = Optional.ofNullable(name).orElseThrow(() -> new IllegalArgumentException("primer must not be null"));
        validateAsPrimerSequence(basePairSequence);
        this.basePairSequence = basePairSequence;
        this.primerId = Objects.requireNonNull(primerId,"primer id was null");
    }

    private static void validateAsPrimerSequence(BasePairSequence sequence){
        if( sequence.numberOfBasePairs() < 6) {
            throw new IllegalArgumentException("primer sequence too short : %s bases".formatted(sequence.numberOfBasePairs()));
        }
        if( sequence.numberOfBasePairs() > 120) {
            throw new IllegalArgumentException("primer sequence too long : %s bases".formatted(sequence.numberOfBasePairs()));
        }
    }
    public BasePairSequence basePairSequence() {
        return basePairSequence;
    }

    public PrimerId id() {
        return primerId;
    }

    public PrimerName name() {
        return name;
    }

    public void changeName(String newName) {
        name = Optional.ofNullable(newName)
                .map(PrimerName::new)
                .orElseThrow(() -> new IllegalArgumentException("new name must not be null"));
    }
}
