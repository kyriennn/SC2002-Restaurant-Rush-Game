package control;
import entities.CriticCustomer;
import entities.Customer;
import entities.RegularCustomer;
import entities.VIPCustomer;

import java.util.Arrays;

public class GameConfig {
    //setting game constants
    public final static int MAX_TURNS = 18;
    public final static int NUM_TABLES = 2;
    public final static int TARGET_PAID = 4;
    public final static int TARGET_REVENUE = 4500;  // take money as integer cents according to project brief
    public final static int TARGET_AVG_SATISFACTION = 60;
    public final static int HAPPY_HOUR_RECOVERY = 15;
    //use Integer because Arrays library accepts Objects instead of primitive types
    private final static Integer[] TURNS = {1, 4, 7, 10, 13, 16};
    //counter to keep track of next customer id


    //generate randomness for customer orders
    private final long seed;

    public GameConfig(long seed){
        this.seed = seed;
    }

    //function to return the customer of the current turn, null if none
    public Customer returnCustomerthisTurn(int turn){
        if(Arrays.asList(TURNS).contains(turn)){
            if(turn == 10){
                return new CriticCustomer(turn);
            }
            if(turn == 4){
                return new VIPCustomer(turn);
            }
            else{
                return new RegularCustomer(turn);
            }
        }
        return null;
    }

    public long getSeed(){
        return seed;
    }
}
