package com.bwell.base.content;

import com.bwell.base.content.model.ContentElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<ContentElement, Long> {
}
