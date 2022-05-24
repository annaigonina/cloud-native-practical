package com.ezgroceries.cocktail;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CocktailService {
    private final CocktailDBClient cocktailDBclient;
    private final CocktailRepository cocktailRepository;

    public CocktailService(CocktailDBClient cocktailDBclient, CocktailRepository cocktailRepository) {
        this.cocktailDBclient = cocktailDBclient;
        this.cocktailRepository = cocktailRepository;
    }

    public List<CocktailResource> getCocktails(String search) {
        CocktailDBResponse response = cocktailDBclient.searchCocktails(search);
        if (response != null) {
            List<CocktailDBResponse.DrinkResource> drinks = response.getDrinks();
            if (drinks != null) {
                return mergeCocktails(drinks);
            }
        }
        return null;
    }

    public List<CocktailResource> mergeCocktails(List<CocktailDBResponse.DrinkResource> drinks) {
        //Get all the idDrink attributes
        List<String> ids = drinks.stream().map(CocktailDBResponse.DrinkResource::getIdDrink).collect(Collectors.toList());

        //Get all the ones we already have from our DB, use a Map for convenient lookup
        Map<String, CocktailEntity> existingEntityMap = cocktailRepository.findByDrinkIdIn(ids).stream().collect(Collectors.toMap(CocktailEntity::getDrinkId, o -> o, (o, o2) -> o));

        //Stream over all the drinks, map them to the existing ones, persist a new one if not existing
        Map<String, CocktailEntity> allEntityMap = drinks.stream().map(drinkResource -> {
            CocktailEntity cocktailEntity = existingEntityMap.get(drinkResource.getIdDrink());
            if (cocktailEntity == null) {
                CocktailEntity newCocktailEntity = new CocktailEntity();
                newCocktailEntity.setCocktailId(UUID.randomUUID());
                newCocktailEntity.setDrinkId(drinkResource.getIdDrink());
                newCocktailEntity.setName(drinkResource.getStrDrink());
                newCocktailEntity.setIngredients(getIngredients(drinkResource));
                cocktailEntity = cocktailRepository.save(newCocktailEntity);
            }
            return cocktailEntity;
        }).collect(Collectors.toMap(CocktailEntity::getDrinkId, o -> o, (o, o2) -> o));

        //Merge drinks and our entities, transform to CocktailResource instances
        return mergeAndTransform(drinks, allEntityMap);
    }

    private Set<String> getIngredients(CocktailDBResponse.DrinkResource drinkResource) {
        return new HashSet<>(drinkResource.getIngredients());
    }

    private List<CocktailResource> mergeAndTransform(List<CocktailDBResponse.DrinkResource> drinks, Map<String, CocktailEntity> allEntityMap) {
        return drinks.stream().map(drinkResource -> new CocktailResource(allEntityMap.get(drinkResource.getIdDrink()).getCocktailId(), drinkResource.getStrDrink(), drinkResource.getStrGlass(),
                drinkResource.getStrInstructions(), drinkResource.getStrDrinkThumb(), drinkResource.getIngredients())).collect(Collectors.toList());
    }
}
