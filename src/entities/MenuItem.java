package entities;

import java.util.Arrays;
import java.util.List;

public class MenuItem {
    public static final MenuItem SALAD = new MenuItem("Salad", 900, 1);
    public static final MenuItem BURGER = new MenuItem("Burger", 1500, 1);
    public static final MenuItem PASTA = new MenuItem("Pasta", 1800, 2);

    //menu
    private static final List<MenuItem> STAGE_ONE_MENU =
            Arrays.asList(SALAD, BURGER, PASTA);
    private static final List<MenuItem> STAGE_THREE_MENU =
            Arrays.asList(
                    SALAD,
                    BURGER,
                    PASTA,
                    new ComboMeal(SALAD, BURGER),
                    new ComboMeal(SALAD, PASTA),
                    new ComboMeal(BURGER, PASTA));

    private final String name;
    private final int priceCents;
    private final int preparationUnits;

    public MenuItem(String name, int priceCents, int preparationUnits) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        this.name = name.trim();

        if (this.name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be blank");
        }
        if (priceCents < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }
        if (preparationUnits < 1) {
            throw new IllegalArgumentException("preparation units must be positive");
        }

        this.priceCents = priceCents;
        this.preparationUnits = preparationUnits;
    }

    public static List<MenuItem> stageOneMenu() {
        return STAGE_ONE_MENU;
    }

    public static List<MenuItem> stageThreeMenu() {
        return STAGE_THREE_MENU;
    }

    public String getName() {
        return name;
    }

    public int getPriceCents() {
        return priceCents;
    }

    public int getPrice() {
        return priceCents;
    }

    public int getPreparationUnits() {
        return preparationUnits;
    }

    public int getPrepUnits() {
        return preparationUnits;
    }

    @Override
    public String toString() {
        return name;
    }
}