package se.allfader.geneseq.domain.primer;

import org.junit.jupiter.api.Test;
import se.allfader.geneseq.domain.primitives.BasePairSequence;
import se.allfader.geneseq.testdata.RandomSequence;

import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class PrimerTest {

    @Test
    void shoudlNotAllowLengthShorterThan6() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Primer(new PrimerId(UUID.randomUUID()), new BasePairSequence(UUID.randomUUID(), RandomSequence.dnaSequence(5)));
        });

        assertEquals("primer sequence too short : 5 bases", illegalArgumentException.getMessage());
    }

    @Test
    void shouldAllowLength6To120(){
        IntStream.rangeClosed(6,120)
                .forEach(value -> assertDoesNotThrow(() -> new Primer(new PrimerId(UUID.randomUUID()),
                        new BasePairSequence(UUID.randomUUID(), RandomSequence.dnaSequence(value)))));
    }

    @Test
    void shouldNotAllowLengthLongerThan120() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Primer(new PrimerId(UUID.randomUUID()), new BasePairSequence(UUID.randomUUID(), RandomSequence.dnaSequence(121)));
        });

        assertEquals("primer sequence too long : 121 bases", illegalArgumentException.getMessage());
    }
}