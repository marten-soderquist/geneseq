package se.allfader.geneseq.application.query.sequence;

import jakarta.enterprise.context.ApplicationScoped;
import se.allfader.geneseq.application.query.pagination.PageOf;

import java.util.List;

@ApplicationScoped
public class ListSequencesImpl implements ListSequences {

    private final StoredSequenceRepository storedSequenceRepository;

    public ListSequencesImpl(StoredSequenceRepository storedSequenceRepository) {
        this.storedSequenceRepository = storedSequenceRepository;
    }

    @Override
    public Response getPage(Command command) {
        if(!command.user().canListSequences()) {
            return new Response.Error.Forbidden();
        }

        long numSequences = storedSequenceRepository.count();
        List<StoredSequence> storedSequences = storedSequenceRepository.pageOfSequences(command.page().pageNumber(), command.page().pageSize());

        StoredSequencePage page = new StoredSequencePage(command.page(), storedSequences);

        return new Response.Success(page, numSequences);
    }
}
