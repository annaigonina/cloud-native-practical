package com.ezgroceries.shoppinglist;

import com.ezgroceries.cocktail.CocktailEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "SHOPPING_LIST")
public class ShoppingListEntity {
    @Id
    @Column(name = "ID")
    private UUID shoppingListId;
    private String name;

    @ManyToMany(mappedBy = "shopping_list")
    private Set<CocktailEntity> cocktails = new HashSet<>();

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

   /* public Set<CocktailEntity> getCocktails() {
        return cocktails;
    }

    public void setCocktails(Set<CocktailEntity> cocktails) {
        this.cocktails = cocktails;
    }*/
}
