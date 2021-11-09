package com.bwell.modules.base.entry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findAllByModuleEquals(String module);
}
