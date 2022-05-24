package com.ezgroceries.shoppinglist;

import com.ezgroceries.cocktail.CocktailEntity;
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
            return new ResponseEntity<>(shoppingListService.createShoppingList(newShoppingList), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<List<CocktailEntity>> addCocktails(@PathVariable UUID shoppingListId, @RequestBody List<CocktailEntity> cocktails) {
        if (shoppingListId != null && cocktails != null) {
            return new ResponseEntity<>(shoppingListService.addCocktails(shoppingListId, cocktails), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/shopping-lists/{shoppingListId}")
    public ResponseEntity<ShoppingListResource> get(@PathVariable UUID shoppingListId) {
        if (shoppingListId != null) {
            return new ResponseEntity<>(shoppingListService.getShoppingList(shoppingListId), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/shopping-lists")
    public ResponseEntity<List<ShoppingListResource>> getAll() {
        List<ShoppingListResource> allShoppingLists = shoppingListService.getAllShoppingLists();
        return new ResponseEntity<>(allShoppingLists, HttpStatus.OK);
    }
}
