package se.allfader.geneseq.infrastructure.persistence.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    private final String hashValue;

    private Hash(String hashValue) {
        if (hashValue == null || hashValue.length() != 64) {
            throw new IllegalArgumentException("Hash value must not be null and length must be 64");
        }
        this.hashValue = hashValue;
    }

    public String getHashValue() {
        return hashValue;
    }

    public static Hash fromString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            String hashValue = bytesToHex(hashBytes);
            return new Hash(hashValue);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to find SHA-256 MessageDigest", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xff & aByte);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
