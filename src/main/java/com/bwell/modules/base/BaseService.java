package com.bwell.modules.base;

public abstract class BaseService {
    protected final ContentRepository content;
    protected final EntryRepository entry;
    protected final RatingRepository rating;

    public BaseService(ContentRepository content, EntryRepository entry, RatingRepository rating) {
        this.content = content;
        this.entry = entry;
        this.rating = rating;
    }
}
