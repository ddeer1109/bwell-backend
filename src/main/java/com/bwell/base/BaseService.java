package com.bwell.base;

import com.bwell.base.content.ContentRepository;
import com.bwell.base.entry.Entry;
import com.bwell.base.entry.EntryRepository;
import com.bwell.base.rating.repository.RatingRepository;
import com.bwell.security.UserPrincipal;
import com.bwell.user.data.model.User;
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

    public EntryRepository getEntry() {
        return entry;
    }

    @Override
    public Entry saveEntry(Entry entry) {
        rating.save(entry.getRating());
        content.saveAll(entry.getContent());
        Entry tempEntry = this.entry.save(entry);

        log.info("Returning {}", tempEntry);

        return tempEntry;
    }
    @Override
    public void saveAll(List<Entry> list) {
        list.forEach(this::saveEntry);
    }

    @Override
    public boolean isAuthor(UserPrincipal user, long entryId){
        try {
            User author = entry.getById(entryId).getAuthor();
            User user1 = userService.getCredentialsById(user.getId()).getUser();
            return user1.getId()
                    .equals(author.getId());
        } catch (NullPointerException e) {
            log.info("msg {}", e);
            return false;
        }

    }

    @Override
    public Entry findEntryById(long id) {
        return entry.findById(id).orElse(null);
    }
}
