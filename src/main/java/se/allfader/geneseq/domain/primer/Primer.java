package se.allfader.geneseq.domain.primer;

import se.allfader.geneseq.domain.core.BasePairSequence;

public class Primer {
    private final BasePairSequence basePairSequence;
    public Primer(BasePairSequence basePairSequence) {
        this.basePairSequence = basePairSequence;
    }

    public BasePairSequence basePairSequence() {
        return basePairSequence;
    }
}
