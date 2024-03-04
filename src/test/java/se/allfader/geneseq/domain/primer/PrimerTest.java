package se.allfader.geneseq.domain.primer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import se.allfader.geneseq.domain.primitives.BasePairSequence;
import se.allfader.geneseq.testdata.RandomSequence;

import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class PrimerTest {
    private static final PrimerName PRIMER_NAME = new PrimerName("Test Primer Name");
    @Test
    void shoudlNotAllowLengthShorterThan6() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Primer(new PrimerId(UUID.randomUUID()), PRIMER_NAME, new BasePairSequence(UUID.randomUUID(), RandomSequence.dnaSequence(5)));
        });

        assertEquals("primer sequence too short : 5 bases", illegalArgumentException.getMessage());
    }

    @Test
    void shouldAllowLength6To120(){
        IntStream.rangeClosed(6,120)
                .forEach(value -> assertDoesNotThrow(() -> new Primer(new PrimerId(UUID.randomUUID()), PRIMER_NAME,
                        new BasePairSequence(UUID.randomUUID(), RandomSequence.dnaSequence(value)))));
    }

    @Test
    void shouldNotAllowLengthLongerThan120() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Primer(new PrimerId(UUID.randomUUID()), PRIMER_NAME, new BasePairSequence(UUID.randomUUID(), RandomSequence.dnaSequence(121)));
        });

        assertEquals("primer sequence too long : 121 bases", illegalArgumentException.getMessage());
    }

    @Test
    void shouldHaveUniqueId() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Primer primer1 = new Primer(new PrimerId(id1), PRIMER_NAME, new BasePairSequence(UUID.randomUUID(), RandomSequence.dnaSequence(10)));
        Primer primer2 = new Primer(new PrimerId(id2), PRIMER_NAME, new BasePairSequence(UUID.randomUUID(), RandomSequence.dnaSequence(10)));

        assertNotEquals(primer1.id(), primer2.id());
    }

    @Test
    void shouldThrowErrorIfNameIsMissing() {
        assertThrows(IllegalArgumentException.class, () -> new Primer(new PrimerId(UUID.randomUUID()), null,
                new BasePairSequence(UUID.randomUUID(), RandomSequence.dnaSequence(67))));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "banan",
            "melon",
            "kiwi",
            "citron",
    })
    void shouldSetNewName(final String newName) {

        Primer primer = new Primer(new PrimerId(UUID.randomUUID()), new PrimerName("OLDNAME"), new BasePairSequence(UUID.randomUUID(), RandomSequence.dnaSequence(45)));

        PrimerName expected = PrimerName.fromString(newName);
        primer.changeName(newName);

        assertEquals(expected, primer.name(), "name not equal to expected");
    }
}