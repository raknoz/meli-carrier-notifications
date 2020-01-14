package com.project.meli.demo.util;

import java.util.List;

public class PagedResult<T> {
    public static final Long DEFAULT_OFFSET = 0L;
    public static final Integer DEFAULT_MAX_NO_OF_ROWS = 100;

    private int offset;
    private int limit;
    private long totalElements;
    private List<T> elements;

    public PagedResult(final List<T> elements, final Long totalElements, final Integer offset, final Integer limit) {
        this.elements = elements;
        this.totalElements = totalElements;
        this.offset = offset;
        this.limit = limit;
    }

    public boolean hasMore() {
        return totalElements > offset + limit;
    }

    public boolean hasPrevious() {
        return offset > 0 && totalElements > 0;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int  getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public List<T> getElements() {
        return elements;
    }
}
