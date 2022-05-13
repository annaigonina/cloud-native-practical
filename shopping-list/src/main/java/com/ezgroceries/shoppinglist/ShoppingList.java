package com.ezgroceries.shoppinglist;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.UUID;

public class ShoppingList {
    private UUID shoppingListId;
    private String name;
    @JsonIgnore
    private List<CocktailResource> cocktails;
    private List<String> ingredients;

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<CocktailResource> getCocktails() {
        return cocktails;
    }


    public void setCocktails(List<CocktailResource> cocktails) {
        this.cocktails = cocktails;
    }

    public ShoppingList(String name) {
        this.name = name;
    }

    public ShoppingList(UUID shoppingListId, String name, List<CocktailResource> cocktails, List<String> ingredients) {
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.cocktails = cocktails;
        this.ingredients = ingredients;
    }

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(UUID shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
