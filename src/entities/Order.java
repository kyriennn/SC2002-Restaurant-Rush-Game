package entities;

import java.util.Objects;

public final class Order {
    private static int generatedIds = 1;

    private final int orderId;
    private final Customer customer;
    private final MenuItem menuItem;
    private final int priceCents;
    private final int requiredPreparationUnits;
    private final int createdTurn;
    private int completedPreparationUnits;
    private int readyTurn = -1;
    private int servedTurn = -1;
    private int paidTurn = -1;
    private OrderStatus status;

    public Order(int orderId, Customer customer, MenuItem menuItem, int createdTurn) {
        if (orderId < 1) {
            throw new IllegalArgumentException("orderId must be positive");
        }
        if (createdTurn < 0) {
            throw new IllegalArgumentException("createdTurn cannot be negative");
        }

        this.orderId = orderId;
        generatedIds = Math.max(generatedIds, orderId + 1);
        this.customer = Objects.requireNonNull(customer, "customer cannot be null");
        this.menuItem = Objects.requireNonNull(menuItem, "menuItem cannot be null");
        this.priceCents = customer.getPriceCents(menuItem);
        this.requiredPreparationUnits = menuItem.getPreparationUnits();
        this.createdTurn = createdTurn;
        this.completedPreparationUnits = 0;
        this.status = OrderStatus.ORDERED;
    }

    public Order(Customer customer, MenuItem menuItem, int createdTurn) {
        this(nextGeneratedId(), customer, menuItem, createdTurn);
    }

    public Order(
            Customer customer,
            MenuItem menuItem,
            int createdTurn,
            int chargedPriceCents) {
        this(nextGeneratedId(), customer, menuItem, createdTurn, chargedPriceCents);
    }

    public Order(
            int orderId,
            Customer customer,
            MenuItem menuItem,
            int createdTurn,
            int chargedPriceCents) {
        if (chargedPriceCents < 0) {
            throw new IllegalArgumentException("charged price cannot be negative");
        }
        if (orderId < 1) {
            throw new IllegalArgumentException("orderId must be positive");
        }
        if (createdTurn < 0) {
            throw new IllegalArgumentException("createdTurn cannot be negative");
        }

        this.orderId = orderId;
        generatedIds = Math.max(generatedIds, orderId + 1);
        this.customer = Objects.requireNonNull(customer, "customer cannot be null");
        this.menuItem = Objects.requireNonNull(menuItem, "menuItem cannot be null");
        this.priceCents = chargedPriceCents;
        this.requiredPreparationUnits = menuItem.getPreparationUnits();
        this.createdTurn = createdTurn;
        this.completedPreparationUnits = 0;
        this.status = OrderStatus.ORDERED;
    }

    static int nextGeneratedId() {
        return generatedIds++;
    }

    /** Performs one preparation unit and reports whether it is now ready. */
    public boolean advancePreparation(int turn) {
        if (status != OrderStatus.ORDERED) {
            throw new IllegalStateException("only an ordered dish can be prepared");
        }
        if (turn < createdTurn) {
            throw new IllegalArgumentException("preparation turn cannot precede the order");
        }

        completedPreparationUnits++;
        if (completedPreparationUnits == requiredPreparationUnits) {
            status = OrderStatus.READY;
            readyTurn = turn;
            return true;
        }
        return false;
    }

    void markServed(int turn) {
        if (status != OrderStatus.READY) {
            throw new IllegalStateException("only a ready order can be served");
        }
        if (turn <= readyTurn) {
            throw new IllegalStateException(
                    "an order cannot be served on the turn it becomes ready");
        }

        status = OrderStatus.SERVED;
        servedTurn = turn;
    }

    void markPaid(int turn) {
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException("only a served order can be paid");
        }
        if (turn <= servedTurn) {
            throw new IllegalStateException(
                    "customer must eat for a full turn before paying");
        }

        status = OrderStatus.PAID;
        paidTurn = turn;
    }

    public void cancel() {
        if (status == OrderStatus.PAID || status == OrderStatus.CANCELLED) {
            throw new IllegalStateException(
                    "paid or cancelled order cannot be cancelled again");
        }
        status = OrderStatus.CANCELLED;
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getPriceCents() {
        return priceCents;
    }

    public int getRequiredPreparationUnits() {
        return requiredPreparationUnits;
    }

    public int getCompletedPreparationUnits() {
        return completedPreparationUnits;
    }

    public int getCreatedTurn() {
        return createdTurn;
    }

    public int getReadyTurn() {
        return readyTurn;
    }

    public int getServedTurn() {
        return servedTurn;
    }

    public int getPaidTurn() {
        return paidTurn;
    }

    public OrderStatus getStatus() {
        return status;
    }
}