package entities;

import java.util.Objects;

// Seats one queued customer per turn, independently of the Waiter
public final class Host extends Staff {
    public Host() {
        this("Host");
    }

    public Host(String name) {
        super(name);
    }

    public void seatCustomer(Customer customer, Table table) {
        if (!canAct()) {
            throw new IllegalStateException("Host has already seated a customer this turn");
        }
        Objects.requireNonNull(table, "table cannot be null").seatCustomer(customer);
        markActed();
    }
}
