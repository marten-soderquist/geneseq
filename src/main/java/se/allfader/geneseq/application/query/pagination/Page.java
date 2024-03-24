package se.allfader.geneseq.application.query.pagination;

public record Page(int pageNumber, int pageSize) {
    public boolean isFirstPage() {
        return pageNumber == 0;
    }
}
