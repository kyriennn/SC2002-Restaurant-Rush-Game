package entities;
import java.util.List;
import java.util.Random;

public final class CriticCustomer extends Customer {

    private static final int SATISFACTION_LOSS_PER_TURN = 12;

    public CriticCustomer(int arrivalTurn) {
        super(arrivalTurn);
    }

    @Override
    public int getSatisfactionLoss() {
        return SATISFACTION_LOSS_PER_TURN;
    }

    @Override 
    public String getTypeName(){
        return "Critic";
    }


    /* Chooses the highest-priced menu item. If prices tie, chooses the first one in menu order. */
    @Override 
    public MenuItem chooseMenuItem(List<MenuItem> menu, Random rng){
        MenuItem best = menu.get(0);
        for(MenuItem item : menu){
            if(item.getPriceCents() > best.getPriceCents()){
                best = item;
            }
        }
        return best;
    }
}
