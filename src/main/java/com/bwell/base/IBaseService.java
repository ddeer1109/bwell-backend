package com.bwell.base;

import com.bwell.base.entry.Entry;
import com.bwell.security.UserPrincipal;

import java.util.List;

public interface IBaseService {
    public Entry addEntry(Entry entry);

    public void saveAll(List<Entry> list);

    public boolean isAuthor(UserPrincipal user, long entryId);
}
