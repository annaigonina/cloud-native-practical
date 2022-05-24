package com.ezgroceries.cocktail;

import com.ezgroceries.shoppinglist.ShoppingListEntity;
import com.ezgroceries.utils.StringSetConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "COCKTAIL")
public class CocktailEntity {
    @Id
    @Column(name = "ID")
    private UUID cocktailId;

    @JsonIgnore
    @Column(name = "ID_DRINK")
    private String drinkId;

    private String name;

    @Convert(converter = StringSetConverter.class)
    private Set<String> ingredients;

    public CocktailEntity(){
    }

    public CocktailEntity(UUID cocktailId, String drinkId, String name, Set<String> ingredients) {
        this.cocktailId = cocktailId;
        this.drinkId = drinkId;
        this.name = name;
        this.ingredients = ingredients;
    }

    public UUID getCocktailId() {
        return cocktailId;
    }

    public String getDrinkId() {
        return drinkId;
    }

    public String getName() {
        return name;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public void setCocktailId(UUID cocktailId) {
        this.cocktailId = cocktailId;
    }

    public void setDrinkId(String drinkId) {
        this.drinkId = drinkId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(Set<String> ingredients) {
        this.ingredients = ingredients;
    }
}
