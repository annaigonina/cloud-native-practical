package com.ezgroceries.shoppinglist;

import com.ezgroceries.cocktail.CocktailEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "SHOPPING_LIST")
public class ShoppingListEntity {
    @Id
    @Column(name = "ID")
    private UUID shoppingListId;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "COCKTAIL_SHOPPING_LIST",
            joinColumns = { @JoinColumn(name = "SHOPPING_LIST_ID")},
            inverseJoinColumns = { @JoinColumn(name = "COCKTAIL_ID")}
    )
    private List<CocktailEntity> cocktails;

    public ShoppingListEntity(){
    }

    public ShoppingListEntity(UUID shoppingListId, String name) {
        this.shoppingListId = shoppingListId;
        this.name = name;
    }

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public String getName() {
        return name;
    }

    public void setShoppingListId(UUID shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CocktailEntity> getCocktails() {
        return cocktails;
    }

    public void setCocktails(List<CocktailEntity> cocktails) {
        this.cocktails = cocktails;
    }
}
