package com.pikachu.recipe_app.model;

public enum RecipeCategory {
    KOREAN("한식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    WESTERN("양식"),
    DESSERT("디저트"),
    ETC("기타");

    private final String displayName;

    RecipeCategory(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
