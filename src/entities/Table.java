public class Table {

    private int tableNumber;
    private Customer customer;

    //declaration
    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.customer = null;
    }

    //getters
    public int getTableNumber() {
        return tableNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    // check if it is occupied
    public boolean isOccupied() {
        return customer != null;
    }

    public void seatCustomer(Customer customer) {
        if (!isOccupied()) {
            this.customer = customer;
            customer.setTable(this);
            customer.setStatus(CustomerStatus.SEATED);
        }
    }

    //Empty table will have null value
    public void freeTable() {
        this.customer = null;
    }

    // allow for override to display different information later on
    @Override
    public String toString() {
        if (isOccupied()) {
            return "Table " + tableNumber +
                   " (Occupied by Customer " +
                   customer.getCustomerId() + ")";
        }
        return "Table " + tableNumber + " (Available)";
    }
}
