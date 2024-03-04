package se.allfader.geneseq.domain.matchscore;

public record MatchScore(String primerId, String sequenceId, int sequencePosition, int matchingBasePairs,
                         int primerLength, Direction direction) {
    public double score() {
        return (double) matchingBasePairs / primerLength;
    }

    public enum Direction {
        FORWARD, REVERSE
    }
}
