package se.allfader.geneseq.api.sequence;

public class SequenceApiDocStrings {
    public static final String addSequenceDoc = """
            Add a new Sequence. A Sequence is represented using the letters A C G T. Input is case-insensitive.
            The input is checked for duplicates when inserted and if a duplicate is found rejected. 
            """;
}
