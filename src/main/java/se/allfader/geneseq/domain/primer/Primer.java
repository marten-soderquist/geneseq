package se.allfader.geneseq.domain.primer;

import se.allfader.geneseq.domain.primitives.BasePairSequence;

public class Primer {
    private final BasePairSequence basePairSequence;
    private final PrimerId primerId;

    public Primer(PrimerId primerId, BasePairSequence basePairSequence) {
        this.basePairSequence = basePairSequence;
        this.primerId = primerId;
    }

    public BasePairSequence basePairSequence() {
        return basePairSequence;
    }

    public PrimerId getPrimerId() {
        return primerId;
    }
}
