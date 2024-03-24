package se.allfader.geneseq.domain.sequence;

import io.smallrye.common.constraint.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import se.allfader.geneseq.domain.primitives.BasePairSequence;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SequenceTest {

    public static final String NAME = "sequence-name";

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

    @ParameterizedTest
    @NullAndEmptySource
    void throwsIllegalArgumentIfMissingName(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Sequence(UUID.randomUUID(),null, new BasePairSequence(UUID.randomUUID(), "aattggcc")));
    }

    private static Sequence testSequence(String sequence) {
        return new Sequence(UUID.randomUUID(), NAME, new BasePairSequence(UUID.randomUUID(), sequence));
    }

}