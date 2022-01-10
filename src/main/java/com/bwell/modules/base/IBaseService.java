package com.bwell.modules.base;

import com.bwell.modules.base.entry.Entry;
import com.bwell.modules.security.UserPrincipal;
import com.bwell.modules.user.data.model.User;

import java.util.List;

public interface IBaseService {
    public Entry addEntry(Entry entry);

    public void saveAll(List<Entry> list);

    public boolean isAuthor(UserPrincipal user, long entryId);
}
