package se.allfader.geneseq.feature;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import se.allfader.geneseq.application.services.SequenceMatcher;
import se.allfader.geneseq.domain.matchscore.MatchScore;
import se.allfader.geneseq.domain.primer.PrimerId;
import se.allfader.geneseq.domain.primer.PrimerName;
import se.allfader.geneseq.domain.primitives.BasePairSequence;
import se.allfader.geneseq.domain.primer.Primer;
import se.allfader.geneseq.domain.sequence.Sequence;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

class SequenceMatcherTest {
    private static final UUID PRIMER_BASE_PAIR_SEQUENCE_ID = UUID.randomUUID();
    private static final PrimerId PRIMER_ID = new PrimerId(UUID.randomUUID());
    private static final UUID SEQUENCE_BASE_PAIR_SEQUENCE_ID = UUID.randomUUID();
    private static final UUID SEQUENCE_ID = UUID.randomUUID();
    private static final String SEQUENCE_NAME = "SequenceNameTest";
    private static final PrimerName PRIMER_NAME = new PrimerName("Test Primer Name");
    private final String testSequence =
            "actcccagcgtaccgaacgacaagcgaggggacgacagaacaagagactgctttcaagtgggtattatattgtaaattactccgtcgaccgaggtaggcg";

    @ParameterizedTest
    @CsvSource(value = {
            "cccttg"
    })
    void test(String primerSequence) {
        Primer primer = new Primer(PRIMER_ID, PRIMER_NAME, new BasePairSequence(PRIMER_BASE_PAIR_SEQUENCE_ID, primerSequence));
        Sequence sequence = new Sequence(SEQUENCE_ID, SEQUENCE_NAME, new BasePairSequence(SEQUENCE_BASE_PAIR_SEQUENCE_ID, testSequence));

        Collection<MatchScore> matchScores = new SequenceMatcher(sequence).matchPrimer(primer);

        Map<MatchScore.Direction, List<MatchScore>> collect = matchScores.stream().collect(Collectors.groupingBy(MatchScore::direction, Collectors.toList()));

        System.out.println(collect);
    }
}