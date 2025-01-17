create table COCKTAIL (
    ID UUID PRIMARY KEY,
    ID_DRINK TEXT,
    NAME TEXT,
    INGREDIENTS TEXT
);

create table COCKTAIL_SHOPPING_LIST (
    COCKTAIL_ID UUID,
    SHOPPING_LIST_ID UUID,
    CONSTRAINT FK_COCKTAIL_ID FOREIGN KEY(COCKTAIL_ID) REFERENCES COCKTAIL(ID),
    CONSTRAINT FK_SHOPPING_LIST_ID FOREIGN KEY(SHOPPING_LIST_ID) REFERENCES SHOPPING_LIST(ID)
);