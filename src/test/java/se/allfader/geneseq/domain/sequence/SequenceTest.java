package se.allfader.geneseq.domain.sequence;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import se.allfader.geneseq.domain.core.BasePairSequence;

import static org.junit.jupiter.api.Assertions.*;

class SequenceTest {

    @ParameterizedTest
    @CsvSource(value = {
            "atc,1, 5,atc..",
            "atc,-2,5,...at",
            "atc,3,5,c...."
    })
    void shouldPadSubSequence(String sequence, int position, int length, String expectedSubsequence){
        String sequenceAtPosition = new Sequence(new BasePairSequence(sequence)).subSequenceAtPosition(position, length);
        assertEquals(expectedSubsequence, sequenceAtPosition, "subsequence does not match");
    }

}