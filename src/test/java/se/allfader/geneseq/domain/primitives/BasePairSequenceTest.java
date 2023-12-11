package se.allfader.geneseq.domain.primitives;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class BasePairSequenceTest {

    @ParameterizedTest
    @CsvSource(value = {
            "atcgatcg,cgatcgat",
            "auugc,gcaat"
    })
    void shouldCreateFromSense(String sense, String expectedReverseAntiSense){
        BasePairSequence basePairSequence = new BasePairSequence(sense);

        System.out.println(basePairSequence.sequence());
        System.out.println(basePairSequence.reverseAntiSense());
        assertEquals(expectedReverseAntiSense, basePairSequence.reverseAntiSense());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "acgcgtcu, 'RNA sequence contains t [pos=6]'",
            "tttacgu, 'RNA sequence contains t [pos=1]'",
            "auucgt, 'RNA sequence contains t [pos=6]'"
    })
    void shouldThrowWhen_U_and_T_inSameSequence(String inputSequence, String expectedErrorMessage){
        var e = assertThrows(IllegalArgumentException.class, () -> new BasePairSequence(inputSequence));
        assertEquals(expectedErrorMessage,e.getMessage());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "ACggTTtt",
            "aacggUU",
            "ATTGTGCaCTG"
    })
    void shouldAllowLowerAndUpperCase(final String testSequence){
        assertDoesNotThrow(() -> new BasePairSequence(testSequence));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "ACgqgTTtt, sequence contains invalid character q [pos=4]",
            "aacggUUX, sequence contains invalid character X [pos=8]",
            "ATTGXGTGCaCTG, sequence contains invalid character X [pos=5]"
    })
    void shouldNotAllowNonBaseCharacters(final String testSequence, String expectedErrorMessage){
        var error = assertThrows(IllegalArgumentException.class, () -> new BasePairSequence(testSequence));
        assertEquals(expectedErrorMessage, error.getMessage());
    }
}