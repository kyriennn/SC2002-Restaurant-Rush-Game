package entities;

public abstract class Customer {

    private int customerId;
    private int satisfaction;
    private CustomerStatus status;
    private Order order;
    private Table table;

    /*
    Abstract base class representing a customer in the restaurant simulation.
    Each customer has a unique ID, a satisfaction level (starts at 100),
    a current status, and optional references to an {@link Order} and a {@link Table}.
    */

    // We start with waiting status and satisfaction of 100 (Decrease from there on out)
    public Customer(int customerId){
        this.customerId = customerId;
        this.satisfaction = 100;
        this.status = CustomerStatus.WAITING;
    }
    
    // Build on from stage 2 onwards
    public abstract int getSatisfactionLoss();

    //return the amount of satisfaction lost per update
    public void decreaseSatisfaction(){
        satisfaction = Math.max(0,
                                satisfaction - getSatisfactionLoss());
    }

    public int getCustomerId(){
        return customerId;
    }

    public int getSatisfaction(){
        return satiscation;
    }

    public CustomerStatus getStatus(){
        return status;
    }

    // Getters and setters
    public void setStatus(CustomerStatus status){
        this.status = status;
    }

    public Order getOrder(){
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
    
}
