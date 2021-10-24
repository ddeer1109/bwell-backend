package com.bwell.modules.base;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<ContentElement, Long> {
}
