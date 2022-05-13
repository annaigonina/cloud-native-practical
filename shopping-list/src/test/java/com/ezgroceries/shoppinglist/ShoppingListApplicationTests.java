package com.ezgroceries.shoppinglist;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ShoppingListApplicationTests {
@Autowired
MockMvc mockMvc;

    @Test
    public void testGetCocktails() throws Exception {
        mockMvc.perform(get("/cocktails?search=test").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateShoppingList() throws Exception {
        ShoppingList testShoppingList = new ShoppingList(UUID.randomUUID(), "Stephanie's birthday", null, null);

        mockMvc.perform(post("/shopping-lists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testShoppingList)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..name").value("Stephanie's birthday"));
    }

    @Test
    public void testAddCocktailsToShoppingList() throws Exception {
        List<CocktailResource> testCocktails = Arrays.asList(
                new CocktailResource(UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4"), null, null, null, null, null),
                new CocktailResource(UUID.fromString("d615ec78-fe93-467b-8d26-5d26d8eab073"),null, null, null, null, null));

        mockMvc.perform(post("/shopping-lists/{shoppingListId}/cocktails",UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(testCocktails)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].cocktailId").value("23b3d85a-3928-41c0-a533-6538a71e17c4"));
    }

    @Test
    public void testGetShoppingList() throws Exception {
        UUID shoppingListId = UUID.randomUUID();
        String name = "Stephanie's birthday";
        List<String> ingredients = Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao");
        ShoppingList testShoppingList = new ShoppingList(shoppingListId, name, null, ingredients);

        mockMvc.perform(get("/shopping-lists/{shoppingListId}",shoppingListId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(testShoppingList)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ingredients[0]").value("Tequila"));
    }

    @Test
    public void testGetAllShoppingLists() throws Exception {
        List<ShoppingList> testShoppingLists = Arrays.asList(
                new ShoppingList(
                        UUID.fromString("4ba92a46-1d1b-4e52-8e38-13cd56c7224c"), "Stephanie's birthday", null,
                        Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao")),
                new ShoppingList(
                        UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465"), "My Birthday", null,
                        Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao")));

        mockMvc.perform(get("/shopping-lists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(testShoppingLists)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[?(@.name=='My Birthday')].ingredients[0]").value("Tequila"));
    }

    protected static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
