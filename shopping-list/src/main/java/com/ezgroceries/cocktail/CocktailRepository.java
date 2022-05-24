package com.ezgroceries.cocktail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface CocktailRepository extends JpaRepository<CocktailEntity, UUID> {
    public List<CocktailEntity> findByDrinkIdIn(List<String> ids);
}
