package se.allfader.geneseq.application.query.sequence;

import java.util.List;

public interface StoredSequenceRepository {
    long count();

    List<StoredSequence> pageOfSequences(int pageNumber, int pageSize);
}
