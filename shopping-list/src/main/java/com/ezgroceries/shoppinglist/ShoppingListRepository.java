package com.ezgroceries.shoppinglist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface ShoppingListRepository extends JpaRepository<ShoppingListEntity, UUID> {
    public ShoppingListEntity findByShoppingListId(UUID id);
}