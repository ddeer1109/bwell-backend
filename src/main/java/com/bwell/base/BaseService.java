package com.bwell.base;

import com.bwell.base.content.ContentRepository;
import com.bwell.base.entry.Entry;
import com.bwell.base.entry.EntryRepository;
import com.bwell.base.rating.repository.RatingRepository;
import com.bwell.security.UserPrincipal;
import com.bwell.user.data.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Primary
public class BaseService implements IBaseService{
    protected final ContentRepository content;
    protected final EntryRepository entry;
    protected final RatingRepository rating;
    protected final UserService userService;

    @Autowired
    public BaseService(ContentRepository content, EntryRepository entry, RatingRepository rating, UserService userService) {
        this.content = content;
        this.entry = entry;
        this.rating = rating;
        this.userService = userService;
    }


    @Override
    public Entry addEntry(Entry entry) {
        rating.save(entry.getRating());
        content.saveAll(entry.getContent());
        Entry tempEntry = this.entry.save(entry);

        log.info("Returning {}", tempEntry);

        return tempEntry;
    }
    @Override
    public void saveAll(List<Entry> list) {
        list.forEach(this::addEntry);
    }

    @Override
    public boolean isAuthor(UserPrincipal user, long entryId){
        return userService.getCredentialsById(user.getId()).getUser().getId()
                .equals(entry.getById(entryId).getAuthor().getId());
    }
}
