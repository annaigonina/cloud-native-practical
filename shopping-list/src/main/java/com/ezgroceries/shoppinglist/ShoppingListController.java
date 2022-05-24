package com.ezgroceries.shoppinglist;

import com.ezgroceries.cocktail.CocktailResource;
import com.ezgroceries.cocktail.CocktailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(produces = "application/json")
public class ShoppingListController {
    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @PostMapping(value = "/shopping-lists")
    public ResponseEntity<ShoppingListResource> create(@RequestBody ShoppingListResource newShoppingList) {
        if (newShoppingList != null && !newShoppingList.getName().isEmpty()) {
            ShoppingListResource shoppingList = shoppingListService.createShoppingList(newShoppingList);
            return new ResponseEntity<>(shoppingList, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<List<CocktailResource>> create(@PathVariable String shoppingListId, @RequestBody List<CocktailResource> cocktails) {
        if (cocktails != null && !shoppingListId.isEmpty()) {
            ShoppingListResource shoppingList = shoppingListService.addCocktails(shoppingListId, cocktails);
            if (shoppingList != null) {
                return new ResponseEntity<>(shoppingList.getCocktails(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/shopping-lists/{shoppingListId}")
    public ResponseEntity<ShoppingListResource> get(@PathVariable String shoppingListId) {
        ShoppingListResource shoppingList = getShoppingListWithIngredients(UUID.fromString(shoppingListId));
        return new ResponseEntity<>(shoppingList, HttpStatus.OK);
    }

    @GetMapping(value = "/shopping-lists")
    public ResponseEntity<List<ShoppingListResource>> get() {
        List<ShoppingListResource> allShoppingLists = getAllShoppingLists();
        return new ResponseEntity<>(allShoppingLists, HttpStatus.OK);
    }

    public ShoppingListResource getShoppingListWithIngredients(UUID shoppingListId){
        String name = "Stephanie's birthday";
        List<String> ingredients = Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao");
        return new ShoppingListResource(shoppingListId,name,null,ingredients);
    }

    public List<ShoppingListResource> getAllShoppingLists(){
        List<ShoppingListResource> allShoppingLists = Arrays.asList(
                new ShoppingListResource(
                        UUID.fromString("4ba92a46-1d1b-4e52-8e38-13cd56c7224c"), "Stephanie's birthday", null,
                        Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao")),
                new ShoppingListResource(
                        UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465"), "My Birthday", null,
                        Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao")));
        return allShoppingLists;
    }
}
