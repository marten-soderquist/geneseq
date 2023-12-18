package se.allfader.geneseq.domain.primitives;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BasePairSequenceTest {
    private static final UUID ID = UUID.randomUUID();
    private static final String VALID_SEQUENCE = "atcgatcg";

    @ParameterizedTest
    @CsvSource(value = {
            "atcgatcg,cgatcgat",
            "auugc,gcaat"
    })
    void shouldReturnExpectedReverseAntiSense(String sense, String expectedReverseAntiSense) {
        BasePairSequence basePairSequence = new BasePairSequence(ID, sense);
        assertEquals(expectedReverseAntiSense, basePairSequence.reverseAntiSense());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "acgcgtcu, 'RNA sequence contains t [pos=6]'",
            "tttacgu, 'RNA sequence contains t [pos=1]'",
            "auucgt, 'RNA sequence contains t [pos=6]'"
    })
    void shouldThrowWhen_U_and_T_inSameSequence(String inputSequence, String expectedErrorMessage) {
        var e = assertThrows(IllegalArgumentException.class, () -> new BasePairSequence(ID, inputSequence));
        assertEquals(expectedErrorMessage, e.getMessage());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "ACggTTtt",
            "aacggUU",
            "ATTGTGCaCTG"
    })
    void shouldAllowLowerAndUpperCase(final String testSequence) {
        assertDoesNotThrow(() -> new BasePairSequence(ID, testSequence));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "ACgqgTTtt, sequence contains invalid character q [pos=4]",
            "aacggUUX, sequence contains invalid character x [pos=8]",
            "ATTGXGTGCaCTG, sequence contains invalid character x [pos=5]"
    })
    void shouldNotAllowNonBaseCharacters(final String testSequence, String expectedErrorMessage) {
        var error = assertThrows(IllegalArgumentException.class, () -> new BasePairSequence(ID, testSequence));
        assertEquals(expectedErrorMessage, error.getMessage());
    }

    @Test
    void shouldSetIdFromConstructor() {
        UUID id = UUID.randomUUID();
        BasePairSequence basePairSequence = new BasePairSequence(id, VALID_SEQUENCE);
        assertEquals(id, basePairSequence.id());
    }
    @Test
    void shouldReturnSequence() {
        BasePairSequence basePairSequence = new BasePairSequence(ID, VALID_SEQUENCE);
        assertEquals(VALID_SEQUENCE, basePairSequence.sequence());
    }


}