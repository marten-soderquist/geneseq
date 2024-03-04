package se.allfader.geneseq.application.services;

import se.allfader.geneseq.domain.matchscore.MatchScore;
import se.allfader.geneseq.domain.primer.Primer;
import se.allfader.geneseq.domain.sequence.Sequence;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SequenceMatcher {

    private final Sequence sequence;

    public SequenceMatcher(Sequence sequence) {
        this.sequence = sequence;
    }

    public Collection<MatchScore> matchPrimer(final Primer primer) {

        int primerLength = primer.basePairSequence().numberOfBasePairs();
        final String primerSense = primer.basePairSequence().sense();
        final String primerReverseAntiSense = primer.basePairSequence().reverseAntiSense();

        int firstMatchPosition = -primerLength + 1;
        int lastMatchPosition = sequence.basePairSequence().numberOfBasePairs();
        return IntStream.rangeClosed(firstMatchPosition, lastMatchPosition)
                .mapToObj(p -> new MatchParams(sequence.subSequenceAtPosition(p, primerLength), p, p + primerLength-1))
                .flatMap(matchParams ->Stream.of(
                        matchSense("primerId", "sequendeId", primerSense, matchParams.sensePosition(), matchParams.subSeq()),
                        matchReverseAntiSense("primerId", "sequendeId", primerReverseAntiSense, matchParams.revAntiSensePosition(), matchParams.subSeq())
                        )
                )
                .filter(matchScore -> matchScore.score() > 0.9)
                .toList();
    }

    private MatchScore matchSense(String primerId, String sequenceId, String primerSequence, int sequencePosition, String subSequence){
        return new MatchScore(primerId, sequenceId, sequencePosition,
                countMatchingBases(primerSequence, subSequence),
                primerSequence.length(),
                MatchScore.Direction.FORWARD
        );
    }

    private MatchScore matchReverseAntiSense(String primerId, String sequenceId, String primerSequence, int sequencePosition, String subSequence) {
        return new MatchScore(primerId, sequenceId, sequencePosition,
                countMatchingBases(primerSequence, subSequence),
                primerSequence.length(),
                MatchScore.Direction.REVERSE
        );
    }

    private int countMatchingBases(String primerSequence, String subSequence) {
        if(primerSequence.length() != subSequence.length()) {
            throw new IllegalStateException("primer length does not match padded sub sequence length");
        }

        return IntStream.range(0, primerSequence.length())
                .map(i -> primerSequence.charAt(i) == subSequence.charAt(i) ? 1 : 0)
                .sum();
    }

    record MatchParams(String subSeq, Integer sensePosition, Integer revAntiSensePosition){}

}
