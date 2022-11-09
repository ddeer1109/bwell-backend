package com.bwell.user.favourites;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouritesRepository extends JpaRepository<Favourites, Long> {
}
