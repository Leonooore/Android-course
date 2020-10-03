package com.gmail.elnora.fet.finalcourseproject.data;

import com.gmail.elnora.fet.finalcourseproject.R;

public enum DishTypeEnum {
    MAIN_COURSE("Main course", R.drawable.ic_main_dish),
    SIDE_DISH("Side dish", R.drawable.ic_spaghetti),
    DESSERT("Dessert", R.drawable.ic_sweets),
    APPETIZER("Appetizer", R.drawable.ic_appetizer),
    SALAD("Salad", R.drawable.ic_salad),
    BREAD("Bread", R.drawable.ic_bread),
    BREAKFAST("Breakfast", R.drawable.ic_egg),
    SOUP("Soup", R.drawable.ic_soup),
    BEVERAGE("Beverage", R.drawable.ic_cocktail),
    SAUCE("Sauce", R.drawable.ic_sauces),
    MARINADE("Marinade", R.drawable.ic_steak),
    FINGER_FOOD("Finger food", R.drawable.ic_fast_food),
    SNACK("Snack", R.drawable.ic_snack),
    DRINK("Drink", R.drawable.ic_drink);

    private final String type;
    private final int image;

    DishTypeEnum(final String type, final int image) {
        this.type = type;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    @Override
    public String toString() {
        return type;
    }
}
