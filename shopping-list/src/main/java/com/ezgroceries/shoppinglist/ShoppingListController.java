package com.ezgroceries.shoppinglist;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(produces = "application/json")
public class ShoppingListController {

    @PostMapping(value = "/shopping-lists")
    public ResponseEntity<ShoppingList> create(@RequestBody ShoppingList newShoppingList) {
        ShoppingList shoppingList = createShoppingList(newShoppingList.getName());
        return new ResponseEntity<>(shoppingList, HttpStatus.CREATED);
    }

    @PostMapping(value = "/shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<List<CocktailResource>> create(@PathVariable String shoppingListId, @RequestBody List<CocktailResource> cocktails) {
        ShoppingList shoppingList = new ShoppingList(UUID.fromString(shoppingListId),null,cocktails,null);
        return new ResponseEntity<>(shoppingList.getCocktails(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/shopping-lists/{shoppingListId}")
    public ResponseEntity<ShoppingList> get(@PathVariable String shoppingListId) {
        ShoppingList shoppingList = getShoppingListWithIngredients(UUID.fromString(shoppingListId));
        return new ResponseEntity<>(shoppingList, HttpStatus.OK);
    }

    @GetMapping(value = "/shopping-lists")
    public ResponseEntity<List<ShoppingList>> get() {
        List<ShoppingList> allShoppingLists = getAllShoppingLists();
        return new ResponseEntity<>(allShoppingLists, HttpStatus.OK);
    }

    public ShoppingList createShoppingList(String name){
        UUID shoppingListId = UUID.randomUUID();
        return new ShoppingList(shoppingListId,name,null,null);
    }

    public ShoppingList getShoppingListWithIngredients(UUID shoppingListId){
        String name = "Stephanie's birthday";
        List<String> ingredients = Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao");
        return new ShoppingList(shoppingListId,name,null,ingredients);
    }

    public List<ShoppingList> getAllShoppingLists(){
        List<ShoppingList> allShoppingLists = Arrays.asList(
                new ShoppingList(
                        UUID.fromString("4ba92a46-1d1b-4e52-8e38-13cd56c7224c"), "Stephanie's birthday", null,
                        Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao")),
                new ShoppingList(
                        UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465"), "My Birthday", null,
                        Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao")));
        return allShoppingLists;
    }
}
