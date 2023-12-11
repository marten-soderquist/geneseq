package se.allfader.geneseq.domain.sequence;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public record SequenceId(byte [] value) {
    public static SequenceId from(final String sequence) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(sequence.getBytes(StandardCharsets.UTF_8));
            return new SequenceId(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Couldn't find SHA-256 algorithm", e);
        }
    }
}
