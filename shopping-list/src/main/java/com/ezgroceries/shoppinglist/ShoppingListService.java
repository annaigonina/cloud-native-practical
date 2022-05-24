package com.ezgroceries.shoppinglist;

import com.ezgroceries.cocktail.CocktailEntity;
import com.ezgroceries.cocktail.CocktailRepository;
import com.ezgroceries.cocktail.CocktailResource;
import com.sun.istack.NotNull;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    public ShoppingListResource createShoppingList(ShoppingListResource newShoppingList) {
        newShoppingList.setShoppingListId(UUID.randomUUID());
        ShoppingListEntity newShoppingListEntity = new ShoppingListEntity(newShoppingList.getShoppingListId(),newShoppingList.getName());
        ShoppingListEntity shoppingListEntity = shoppingListRepository.save(newShoppingListEntity);
        return newShoppingList;
    }

    public List<CocktailEntity> addCocktails(UUID id, List<CocktailEntity> cocktailsToAdd) {
        ShoppingListEntity shoppingListEntity = shoppingListRepository.findByShoppingListId(id);
        if (shoppingListEntity != null) {
            shoppingListEntity.setCocktails(cocktailsToAdd);
            shoppingListRepository.save(shoppingListEntity);
            return cocktailsToAdd;
        }
        return null;
    }

    public ShoppingListResource getShoppingList(UUID id) {
        ShoppingListEntity shoppingListEntity = shoppingListRepository.findByShoppingListId(id);
        if (shoppingListEntity != null) {
            Set<String> ingredients = new HashSet<>();
            for (CocktailEntity cocktailEntity : shoppingListEntity.getCocktails()) {
                ingredients.addAll(cocktailEntity.getIngredients());
            }
            List<String> ingredientsList = new ArrayList<>(ingredients);
            return new ShoppingListResource(id, shoppingListEntity.getName(), null, ingredientsList);
        }
        return null;
    }

    public List<ShoppingListResource> getAllShoppingLists() {
        List<ShoppingListResource> shoppingLists = new ArrayList<>();
        for (ShoppingListEntity shoppingListEntity : shoppingListRepository.findAll()) {
            shoppingLists.add(getShoppingList(shoppingListEntity.getShoppingListId()));
        }
        return shoppingLists;
    }
}
