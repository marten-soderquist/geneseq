package se.allfader.geneseq.domain.primer;

import se.allfader.geneseq.domain.primitives.BasePairSequence;

import java.util.Objects;
import java.util.Optional;

public class Primer {
    private final BasePairSequence basePairSequence;
    private final PrimerId primerId;

    public Primer(PrimerId primerId, BasePairSequence basePairSequence) {
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

    public PrimerId getPrimerId() {
        return primerId;
    }
}
