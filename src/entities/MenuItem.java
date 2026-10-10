public class MenuItem {

    private String name;
    private int price;        // cents
    private int prepUnits;

    public MenuItem(String name,
                    int price,
                    int prepUnits) {

        this.name = name;
        this.price = price;
        this.prepUnits = prepUnits;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getPrepUnits() {
        return prepUnits;
    }

    @Override
    public String toString() {
        return name;
    }
}
