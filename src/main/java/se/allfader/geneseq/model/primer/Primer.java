package se.allfader.geneseq.model.primer;

import se.allfader.geneseq.model.core.BasePairSequence;

public class Primer {
    private final BasePairSequence basePairSequence;
    public Primer(BasePairSequence basePairSequence) {
        this.basePairSequence = basePairSequence;
    }

    public BasePairSequence basePairSequence() {
        return basePairSequence;
    }
}
