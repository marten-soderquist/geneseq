package se.allfader.geneseq.testdata;

import com.github.javafaker.Faker;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class provides methods to generate random DNA and RNA sequences.
 */
public class RandomSequence {

    private static final String DNA_BASES = "actg";
    private static final String RNA_BASES = "acug";
    private static final Faker faker = new Faker();
    private static final Random random = new Random(System.currentTimeMillis());
    /**
     * Generates a random DNA sequence of the specified length.
     *
     * @param length the length of the DNA sequence to generate
     * @return a random DNA sequence of the specified length
     */
    public static String dnaSequence(final int length){
        return IntStream.range(0, length)
                .mapToObj(i -> String.valueOf(DNA_BASES.charAt(random.nextInt(DNA_BASES.length()))))
                .collect(Collectors.joining());
    }

    /**
     * Generates a random RNA sequence of the specified length.
     *
     * @param length the length of the RNA sequence to generate
     * @return a random RNA sequence of the specified length
     */
    public static String rnaSequence(final int length) {
        return IntStream.range(0, length)
                .mapToObj(i -> String.valueOf(RNA_BASES.charAt(random.nextInt(RNA_BASES.length()))))
                .collect(Collectors.joining());
    }


}
