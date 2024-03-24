package se.allfader.geneseq.application.query.sequence;

import java.util.UUID;

public interface StoredSequence {
    UUID id();
    String name();
    String basepairs();
}
