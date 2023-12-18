package se.allfader.geneseq.domain.primer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class PrimerNameTest {
    @ParameterizedTest
    @CsvSource(value = {
            "A valid name",
            "THBE-1233"
    })
    void shouldSetName(final String input) {
        PrimerName primerName = assertDoesNotThrow(() -> PrimerName.fromString(input), "should not throw");
        assertEquals(input, primerName.toString(), "should be equal to supplied string");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "ALongPrimerNameWithMoreThan18Characters"
    })
    void shouldNotAllowNameLongerThan18(final String input) {
        assertPrimerNameThrows(input, "name must not exceed 18 characters");
    }

    @ParameterizedTest
    @NullSource
    void shouldNotAllowNullName(final String input) {
        assertPrimerNameThrows(input, "must not be null");
    }

    @ParameterizedTest
    @EmptySource
    void shouldNotAllowEmptyName(final String input) {
        assertPrimerNameThrows(input, "must not be empty");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "aabcbc=",
            "!BBBAN"
    })
    void shouldNotAllowInvalidCharacters(final String input) {
        assertPrimerNameThrows(input, "contains invalid characters");
    }

    private void assertPrimerNameThrows(String input, String expectedExceptionMessage) {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> PrimerName.fromString(input));
        assertEquals(expectedExceptionMessage, illegalArgumentException.getMessage(),
                "wrong error message");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "TBE8",
            "THBB 77"
    })
    void shoudlBeEqualIfContainsSameString(final String input) {
        PrimerName primerName1 = PrimerName.fromString(input);
        PrimerName primerName2 = PrimerName.fromString(input);
        assertEquals(primerName1, primerName2, "should be equal");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "  abcf",
            "a aaa  ",
            " aaaa                          "
    })
    void shouldTrimLeadingAndTrailingWhiteSpace(final String input) {
        PrimerName primerName = PrimerName.fromString(input);
        assertEquals(input.trim(), primerName.toString(), "should be equal after trimming input");
    }
}