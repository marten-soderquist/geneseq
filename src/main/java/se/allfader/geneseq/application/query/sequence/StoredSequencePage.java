package se.allfader.geneseq.application.query.sequence;

import se.allfader.geneseq.application.query.pagination.Page;
import se.allfader.geneseq.application.query.pagination.PageOf;

import java.util.List;

public class StoredSequencePage extends PageOf<List<StoredSequence>> {

    public StoredSequencePage(Page page, List<StoredSequence> content) {
        super(page, content);
    }
}
