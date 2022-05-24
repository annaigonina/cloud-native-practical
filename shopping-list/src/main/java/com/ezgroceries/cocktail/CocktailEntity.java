package com.ezgroceries.cocktail;

import com.ezgroceries.shoppinglist.ShoppingListEntity;
import com.ezgroceries.utils.StringSetConverter;

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

    @Column(name = "ID_DRINK")
    private String drinkId;

    private String name;

    @Convert(converter = StringSetConverter.class)
    private Set<String> ingredients;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "COCKTAIL_SHOPPING_LIST",
            joinColumns = { @JoinColumn(name = "COCKTAIL_ID")},
            inverseJoinColumns = { @JoinColumn(name = "SHOPPING_LIST_ID")}
    )
    private Set<ShoppingListEntity> shoppingLists = new HashSet<>();

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