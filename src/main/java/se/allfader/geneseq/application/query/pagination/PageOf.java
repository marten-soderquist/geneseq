package se.allfader.geneseq.application.query.pagination;

import java.util.Optional;

public class PageOf<T> {
    private final Page page;
    private Page next;
    private Page previous;
    private final T content;

    public PageOf(Page page, T content) {
        this.page = page;
        this.content = content;
    }

    public Page current() {
        return page;
    }

    public PageOf<T> withNextPage(final Page nextPage) {
        this.next = nextPage;
        return this;
    }

    public PageOf<T> withPreviousPage(final Page previousPage) {
        this.previous = previousPage;
        return this;
    }

    public T content() {
        return content;
    }


    Optional<Page> next() {
        return Optional.ofNullable(next);
    }

    public Optional<Page> previous() {
        return Optional.ofNullable(previous);
    }

}
