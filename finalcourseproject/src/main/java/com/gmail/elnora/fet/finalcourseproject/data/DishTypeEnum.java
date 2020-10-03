package com.gmail.elnora.fet.finalcourseproject.data;

public enum DishTypeEnum {
    MAIN_COURSE("Main course"),
    SIDE_DISH("Side dish"),
    DESSERT("Dessert"),
    APPETIZER("Appetizer"),
    SALAD("Salad"),
    BREAD("Bread"),
    BREAKFAST("Breakfast"),
    SOUP("Soup"),
    BEVERAGE("Beverage"),
    SAUCE("Sauce"),
    MARINADE("Marinade"),
    FINGER_FOOD("Finger food"),
    SNACK("Snack"),
    DRINK("Drink");

    private final String type;

    DishTypeEnum(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
