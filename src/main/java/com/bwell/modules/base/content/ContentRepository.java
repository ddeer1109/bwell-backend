package com.bwell.modules.base.content;

import com.bwell.modules.base.content.model.ContentElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<ContentElement, Long> {
}
