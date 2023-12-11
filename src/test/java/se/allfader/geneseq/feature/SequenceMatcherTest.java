package se.allfader.geneseq.feature;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
    private static final UUID SEQUENCE_BASE_PAIR_SEQUENCE_ID = UUID.randomUUID();
    private final String testSequence =
            "actcccagcgtaccgaacgacaagcgaggggacgacagaacaagagactgctttcaagtgggtattatattgtaaattactccgtcgaccgaggtaggcg";

    @ParameterizedTest
    @CsvSource(value = {
            "ccc"
    })
    void test(String primerSequence) {
        Primer primer = new Primer(new BasePairSequence(PRIMER_BASE_PAIR_SEQUENCE_ID, primerSequence));
        Sequence sequence = new Sequence(new BasePairSequence(SEQUENCE_BASE_PAIR_SEQUENCE_ID, testSequence));

        Collection<SequenceMatcher.MatchScore> matchScores = new SequenceMatcher(sequence).matchPrimer(primer);

        Map<SequenceMatcher.MatchScore.MatchType, List<SequenceMatcher.MatchScore>> collect = matchScores.stream().collect(Collectors.groupingBy(SequenceMatcher.MatchScore::matchType, Collectors.toList()));

        System.out.println(collect);
    }
}