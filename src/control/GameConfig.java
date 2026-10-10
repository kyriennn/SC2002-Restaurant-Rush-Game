package control;
import entities.Customer;
import entities.RegularCustomer;
import java.util.Arrays;

public class GameConfig {
    //setting game constants
    public final static int MAX_TURNS = 18;
    public final static int NUM_TABLES = 2;
    public final static int TARGET_PAID = 4;
    public final static int TARGET_REVENUE = 4500;  // take money as integer cents according to project brief
    public final static int TARGET_AVG_SATISFACTION = 60;
    //use Integer because Arrays library accepts Objects instead of primitive types
    private final static Integer[] TURNS = {1, 4, 7, 10, 13, 16};
    //counter to keep track of next customer id
    private int nextCustomerID = 1;

    //generate randomness for customer orders
    private final long seed;

    public GameConfig(long seed){
        this.seed = seed;
    }

    //function to return the customer of the current turn, null if none
    public Customer returnCustomerthisTurn(int turn){
        if(Arrays.asList(TURNS).contains(turn)){
            // waiting for Customer fields to be implemented, needs satisfaction, name/id, status from CustomerStatus, arrivalTurn, readytoPayTurn, nextCustomerID
            return new RegularCustomer();
        }
        return null;
    }

    public long getSeed(){
        return seed;
    }
}
