package se.allfader.geneseq.domain.sequence;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import se.allfader.geneseq.domain.primitives.BasePairSequence;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SequenceTest {

    @ParameterizedTest
    @CsvSource(value = {
            "atc,1, 5,atc..",
            "atc,-2,5,...at",
            "atc,3,5,c...."
    })
    void shouldPadSubSequenceWithPeriod(String sequence, int position, int length, String expectedSubsequence) {
        String sequenceAtPosition = testSequence(sequence).subSequenceAtPosition(position, length);
        assertEquals(expectedSubsequence, sequenceAtPosition, "subsequence does not match");
    }

    private static Sequence testSequence(String sequence) {
        return new Sequence(UUID.randomUUID(), new BasePairSequence(UUID.randomUUID(), sequence));
    }

}