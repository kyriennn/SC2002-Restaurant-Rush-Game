package control;
import entities.*;
import boundaries.ControlUI;
import boundaries.ControlUI.MenuOption;
import java.util.function.Supplier;

import java.util.*;

public class TurnController {
    private final GameConfig config;
    private final Restaurant restaurant;
    private final ControlUI ui;
    private final Random rng;
    private final HappyHour happyHour = new HappyHour();

    private int currentTurn = 0;

    public TurnController(GameConfig config, ControlUI ui){
        this.config = config;
        this.ui = ui;
        this.restaurant = new Restaurant(GameConfig.NUM_TABLES);
        this.rng = new Random(config.getSeed());
    }

    public void run(){
        ui.showWelcome(config.getSeed());
        for(currentTurn = 1; currentTurn <= GameConfig.MAX_TURNS; currentTurn++){
            playTurn();
        }
        ui.showFinalResult(checkVictory(), GameConfig.MAX_TURNS, restaurant.getServedCount(), restaurant.getPaidCount(), restaurant.getUnhappyDepartures(), restaurant.getRevenueCents(), averageOrNull());
    }

    private void playTurn(){
        resetStaff();
        activateHappyHourAtTurnStart();
        handleArrivals();
        recoverDuringHappyHour();
        ui.showTurnStart(
                currentTurn,
                GameConfig.MAX_TURNS,
                restaurant.getRevenueCents(),
                averageOrNull(),
                happyHour.getRemainingTurns(currentTurn),
                waitingQueueLine(),
                occupiedTableLine(),
                customerStateLine());
        hostPhase();
        waiterPhase();
        chefPhase();
        eatingAndPayment();
        endOfTurnDecay();
        ui.showTurnResults(currentTurn, restaurant.getServedCount(), restaurant.getPaidCount(), restaurant.getUnhappyDepartures(), restaurant.getRevenueCents(), averageOrNull());
    }

    private void activateHappyHourAtTurnStart() {
        if (happyHour.canActivate() && ui.askHappyHourActivation()) {
            happyHour.activate(currentTurn);
            ui.showEvent("Happy Hour is active for this turn and the next.");
        }
    }

    private void recoverDuringHappyHour() {
        if (happyHour.isActive(currentTurn)) {
            restaurant.recoverWaitingCustomers(HappyHour.RECOVERY_POINTS);
            ui.showEvent("Waiting and unserved customers recover 15 satisfaction.");
        }
    }

    private void hostPhase() {
        if (restaurant.getWaitingQueue().isEmpty()
                || restaurant.getFreeTables().isEmpty()) {
            return;
        }

        Customer customer = restaurant.getWaitingQueue().iterator().next();
        Table table = restaurant.getFreeTables().get(0);
        ActionResult result = restaurant.hostSeatCustomer(customer, table);
        if (result.isSuccess()) {
            ui.showActionSucceeded(result.getMessage());
        } else {
            ui.showActionRejected(result.getMessage());
        }
    }

    private void resetStaff(){
        for(Staff staff : restaurant.getAllStaff()){
            staff.resetForNewTurn();
        }
    }

    private void handleArrivals(){
        Customer c = config.returnCustomerthisTurn(currentTurn);
        if(c != null){
            restaurant.addToQueue(c);
            ui.showArrival("Customer " + c.getCustomerId() + " | Type: " + c.getTypeName());
        }
    }

    //build waiter's options for customers
    private Map<MenuOption, Supplier<ActionResult>> buildWaiterOptions(){
        Map<MenuOption, Supplier<ActionResult>> actions = new LinkedHashMap<>();

        //seat method
        for(Customer c : restaurant.getWaitingQueue()){
            for(Table t : restaurant.getFreeTables()){
                actions.put(new MenuOption("SEAT " + c.getCustomerId() + " : " + t.getTableNumber(), "Seat Customer " + c.getCustomerId() + " (Type: " + c.getTypeName() + ") at Table " + t.getTableNumber()), () -> restaurant.seatCustomer(c, t));
            }
        }

        //take order method
        for(Customer c : restaurant.getSeatedWithoutOrder()){
            actions.put(new MenuOption("ORDER " + c.getCustomerId(), "Take order from Customer " + c.getCustomerId()), ()-> restaurant.takeOrder(c, rng, currentTurn, happyHour));
        }

        //serve method
        for(Order o : restaurant.getReadyOrders()){
            actions.put(new MenuOption("SERVE " + o.getOrderId(), "Serve " + o.getMenuItem().getName() + " to Customer " + o.getCustomer().getCustomerId()), ()->restaurant.serveOrder(o, currentTurn));
        }

        //wait method for when others are acting or nothing else to do
        actions.put(new MenuOption("WAIT", "Wait"), null);
        return actions;
    }

    //facilitate the waiter's option picking phase
    private void waiterPhase(){
        while(true){
            Map<MenuOption, Supplier<ActionResult>> actions = buildWaiterOptions();
            MenuOption choice = ui.askWaiterTask(new ArrayList<>(actions.keySet()));

            Supplier<ActionResult> action = actions.get(choice);
            if(action == null){
                ui.showEvent("The Waiter waits.");
                return;
            }
            ActionResult result = action.get();
            if(result.isSuccess()){
                ui.showActionSucceeded(result.getMessage());
                restaurant.getWaiter().markActed();
                return;
            }
            ui.showActionRejected(result.getMessage());
        }
    }

    //repeating same methods for Chef now
    private Map<MenuOption, Supplier<ActionResult>> buildChefOptions(){
        Map<MenuOption, Supplier<ActionResult>> actions = new LinkedHashMap<>();

        //cook method
        for(Order o : restaurant.getOrdersinPrep()){
            actions.put(new MenuOption("COOK " + o.getOrderId(), "Cook " + o.getMenuItem().getName() + " for Customer " + o.getCustomer().getCustomerId() + " (" + o.getCompletedPreparationUnits() + "/" + o.getRequiredPreparationUnits() + "units done)."), ()->restaurant.advanceOrder(o, currentTurn));
        }
        actions.put(new MenuOption("WAIT", "Wait"), null);
        return actions;

    }

    private void chefPhase(){
        while(true){
            if (restaurant.getOrdersinPrep().isEmpty()) {
                ui.showEvent("The Chef has nothing to prepare and waits.");
                return;
            }
            Map<MenuOption, Supplier<ActionResult>> actions = buildChefOptions();
            MenuOption choice = ui.askChefTask(new ArrayList<>(actions.keySet()));

            Supplier<ActionResult> action = actions.get(choice);

            if(action == null){
                ui.showEvent("the Chef waits.");
                return;
            }

            ActionResult result = action.get();

            if(result.isSuccess()){
                ui.showActionSucceeded(result.getMessage());
                restaurant.getChef().markActed();
                return;
            }
            ui.showActionRejected(result.getMessage());

        }
    }

    private void eatingAndPayment(){
        for(Customer c : restaurant.getEatingCustomers()){
            if(c.finishEating(currentTurn)){
                ui.showEvent("Customer " + c.getCustomerId() + " has finished eating and is ready to pay.");
            }
        }

        for(Customer c: restaurant.getReadytoPayCustomers()){
            if(c.getReadyToPayTurn() < currentTurn){
                ui.showEvent(restaurant.processPayment(c, currentTurn));
                restaurant.getCashier().markActed();
                break;
            }
        }
    }

    private void endOfTurnDecay(){
        for(Customer c : restaurant.getCustomersLosingSatisfaction()){
            if(restaurant.processEndOfTurn(c)){
                ui.showEvent("Customer " + c.getCustomerId() + " ran out of patience and left.");
            }
        }
    }

    //setting up for showTurnStart method, which will display the waitingQueue and averageSatisfaction each turn
    //requires helper functions

    private List<String> waitingQueueLine(){
        List<String> lines = new ArrayList<>();
        for(Customer c : restaurant.getWaitingQueue()){
            lines.add("Customer " + c.getCustomerId() + " | Type: " + c.getTypeName() + " (Satisfaction: " + c.getSatisfaction() + ")");
        }
        return lines;
    }

    private List<String> occupiedTableLine(){
        List<String> lines = new ArrayList<>();
        for(Table t: restaurant.getTables()){
            if(t.isOccupied()){
                lines.add("Table " + t.getTableNumber() + ": Customer " + t.getCustomer().getCustomerId());
            }
        }
        return lines;
    }

    private List<String> customerStateLine(){
        List<String> lines = new ArrayList<>();
        for(Table t : restaurant.getTables()){
            if(!t.isOccupied()){
                continue;
            }
            Customer c = t.getCustomer();
            Order o = t.getCustomer().getOrder();

            String order = (o==null)
            ? "no order"
            : o.getMenuItem().getName() + " " + o.getCompletedPreparationUnits() + "/" + o.getRequiredPreparationUnits() + " (" + o.getStatus() + ")";

            lines.add("Customer " + c.getCustomerId() + " | Type: " + c.getTypeName() + " | Satisfaction: " + c.getSatisfaction() + " | " + c.getStatus() + " | " + order);
        }
        return lines;
    }

    private Double averageOrNull() {
        OptionalDouble avg = restaurant.getAverageSatisfaction();
        return avg.isPresent() ? avg.getAsDouble() : null;
    }
    
    private boolean checkVictory() {
        Double avg = averageOrNull();
        return restaurant.getPaidCount() >= GameConfig.TARGET_PAID && restaurant.getRevenueCents() >= GameConfig.TARGET_REVENUE && avg != null && avg >= GameConfig.TARGET_AVG_SATISFACTION;
    }
}
