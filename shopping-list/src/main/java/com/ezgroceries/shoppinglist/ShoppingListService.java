package com.ezgroceries.shoppinglist;

import com.ezgroceries.cocktail.CocktailEntity;
import com.ezgroceries.cocktail.CocktailRepository;
import com.ezgroceries.cocktail.CocktailResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final CocktailRepository cocktailRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository, CocktailRepository cocktailRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.cocktailRepository = cocktailRepository;
    }

    @Transactional
    public ShoppingListResource createShoppingList(ShoppingListResource newShoppingList) {
        newShoppingList.setShoppingListId(UUID.randomUUID());
        ShoppingListEntity newShoppingListEntity = new ShoppingListEntity(newShoppingList.getShoppingListId(),newShoppingList.getName());
        ShoppingListEntity shoppingListEntity = shoppingListRepository.save(newShoppingListEntity);
        return newShoppingList;
    }

    public ShoppingListResource addCocktails(String id, List<CocktailResource> cocktails) {
        UUID shoppingListId = UUID.fromString(id);
        ShoppingListEntity shoppingListEntity = shoppingListRepository.findByShoppingListId(shoppingListId);
        if (shoppingListEntity != null) {

            return new ShoppingListResource(shoppingListId,shoppingListEntity.getName(),cocktails,null);
        }
        return null;
    }
}
