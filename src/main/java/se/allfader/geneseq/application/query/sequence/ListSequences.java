package se.allfader.geneseq.application.query.sequence;

import se.allfader.geneseq.application.query.pagination.Page;
import se.allfader.geneseq.application.query.pagination.PageOf;
import se.allfader.geneseq.domain.sequence.Sequence;
import se.allfader.geneseq.domain.user.User;

import java.util.List;

public interface ListSequences {

    record Command(Page page, User user){}

    Response getPage(Command command);

         sealed interface Response permits Response.Success, Response.Error {
            record Success(StoredSequencePage pageOfSequences, long totalItems) implements Response{}
             sealed interface Error extends Response{
                record Forbidden() implements Error{}
             }
         }
}
