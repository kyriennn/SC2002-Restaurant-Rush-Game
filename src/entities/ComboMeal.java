package entities;

// A menu item composed of two ordinary menu items
public final class ComboMeal extends MenuItem {
    private final MenuItem first;
    private final MenuItem second;

    public ComboMeal(MenuItem first, MenuItem second) {
        super(nameOf(first, second), priceOf(first, second), unitsOf(first, second));
        this.first = first;
        this.second = second;
    }

    private static MenuItem component(MenuItem item) {
        if (item == null) {
            throw new IllegalArgumentException("combo component cannot be null");
        }
        if (item instanceof ComboMeal) {
            throw new IllegalArgumentException("a combo component must be an ordinary menu item");
        }
        return item;
    }

    private static String nameOf(MenuItem first, MenuItem second) {
        return component(first).getName() + " + " + component(second).getName() + " Combo";
    }

    private static int priceOf(MenuItem first, MenuItem second) {
        long combined = (long) component(first).getPriceCents() + component(second).getPriceCents();
        return Math.toIntExact((combined * 90 + 50) / 100);
    }

    private static int unitsOf(MenuItem first, MenuItem second) {
        return Math.addExact(component(first).getPreparationUnits(), component(second).getPreparationUnits());
    }

    public MenuItem getFirst() {
        return first;
    }

    public MenuItem getSecond() {
        return second;
    }
}
