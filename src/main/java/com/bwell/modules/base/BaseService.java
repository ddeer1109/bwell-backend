package com.bwell.modules.base;

import com.bwell.modules.base.content.ContentRepository;
import com.bwell.modules.base.entry.Entry;
import com.bwell.modules.base.entry.EntryRepository;
import com.bwell.modules.base.rating.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Primary
public class BaseService {
    protected final ContentRepository content;
    protected final EntryRepository entry;
    protected final RatingRepository rating;

    public BaseService(ContentRepository content, EntryRepository entry, RatingRepository rating) {
        this.content = content;
        this.entry = entry;
        this.rating = rating;
    }

    public Entry addEntry(Entry entry) {
        rating.save(entry.getRating());
        content.saveAll(entry.getContent());
        Entry tempEntry = this.entry.save(entry);

        log.info("Returning {}", tempEntry);

        return tempEntry;
    }

    public void saveAll(List<Entry> list) {
        list.forEach(this::addEntry);
    }
}
