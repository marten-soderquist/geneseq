package se.allfader.geneseq.application.feature;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import se.allfader.geneseq.application.repository.SequenceRepository;
import se.allfader.geneseq.domain.primitives.BasePairSequence;
import se.allfader.geneseq.domain.sequence.Sequence;
import se.allfader.geneseq.domain.user.User;

import java.util.UUID;

@ApplicationScoped
public class AddSequence {
    private final SequenceRepository sequenceRepository;

    public AddSequence(SequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    public Response add(String sequence, User user) {
        return add(sequence, user, 2500);
    }

    public Response add(String sequence, User user, long timeout) {
        Log.info("Sending command");
        return handle(new Command(sequence, user));
    }

    Response handle(Command command) {
        try {
            if (!command.user().canAddNewSequence()) {
                return new Response.Error(Response.Error.Code.FORBIDDEN, "user not authorized to add a new sequence");
            }
            Log.info("Command received");
            Sequence newSequence = createNewSequence(command.sequence());
            Log.info("Sequence created");
            try {
                sequenceRepository.save(newSequence);
            } catch (SequenceRepository.DuplicateConflict duplicateConflict) {
                Log.warn("failed to persist sequence because it already existed");
                return new Response.Error(Response.Error.Code.DUPLICATE, duplicateConflict.getMessage());
            } catch (SequenceRepository.TimeoutError timeoutError) {
                return new Response.Error(Response.Error.Code.TIMEOUT, "save sequence operation timedout");
            }

            Log.info("Persisted");
            return new Response.Success(newSequence.id());
        } catch (IllegalArgumentException err) {
            Log.error(err.getMessage());
            return new Response.Error(Response.Error.Code.INVALID_INPUT, err.getMessage());
        }
    }

    private static Sequence createNewSequence(String seqString) {
        return new Sequence(UUID.randomUUID(), new BasePairSequence(UUID.randomUUID(), seqString));
    }

    private record Command(String sequence, User user) {
    }

    public sealed interface Response permits Response.Success, Response.Error {
        default boolean isSuccess() {
            return this instanceof Success;
        }

        record Success(UUID newSequenceId) implements Response {
        }

        record Error(Code code, String reason) implements Response {
            public enum Code {
                FORBIDDEN, INVALID_INPUT, TIMEOUT, DUPLICATE
            }
        }
    }
}

