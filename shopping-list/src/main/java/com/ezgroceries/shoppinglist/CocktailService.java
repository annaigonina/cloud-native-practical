package com.ezgroceries.shoppinglist;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CocktailService {
    private CocktailDBClient cocktailDBclient;

    public CocktailService(CocktailDBClient cocktailDBclient) {
        this.cocktailDBclient = cocktailDBclient;
    }

    public CocktailDBResponse searchCocktails(String search) {
        return cocktailDBclient.searchCocktails(search);
    }

    public List<CocktailResource> getCocktails(String search) {
        CocktailDBResponse response = searchCocktails(search);
        if (response != null) {
            List<CocktailDBResponse.DrinkResource> drinks = response.getDrinks();
            if (drinks != null) {
                return drinks.stream().map(drinkResource -> new CocktailResource(UUID.randomUUID(), drinkResource.getStrDrink(), drinkResource.getStrGlass(),
                        drinkResource.getStrInstructions(), drinkResource.getStrDrinkThumb(), drinkResource.getIngredients())).collect(Collectors.toList());
            }
        }
        return null;
    }
}
