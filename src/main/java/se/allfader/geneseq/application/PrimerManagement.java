package se.allfader.geneseq.application;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import se.allfader.geneseq.domain.primer.Primer;
import se.allfader.geneseq.domain.primer.PrimerId;
import se.allfader.geneseq.domain.primitives.BasePairSequence;
import se.allfader.geneseq.domain.repository.PrimerRepository;

import java.util.UUID;

@ApplicationScoped
public class PrimerManagement {

    private final PrimerRepository repository;

    public PrimerManagement(PrimerRepository repository) {
        this.repository = repository;
    }

    @WithTransaction
    public Uni<AddPrimerResponse> addNewPrimer(AddPrimerCommand command) {
        return Uni.createFrom().item(command)
                .map(AddPrimerCommand::createNewPrimer)
                .call(repository::addPrimer)
                .map(AddPrimerResponse::withNewPrimerId);
    }

    public record AddPrimerCommand(String name, String sequence) {
        public Primer createNewPrimer() {
            return new Primer(new PrimerId(UUID.randomUUID()),
                    new BasePairSequence(UUID.randomUUID(), sequence));
        }
    }

    public record AddPrimerResponse(String newPrimerId) {
        public static AddPrimerResponse withNewPrimerId(Primer primer){
            return new AddPrimerResponse(primer.getPrimerId().asString());
        }
    }
}
