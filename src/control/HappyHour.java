package control;

// One use per game, active on its activation turn and the following turn
public final class HappyHour {
    public static final int RECOVERY_POINTS = 15;
    private int activationTurn = -1;

    public boolean canActivate() {
        return activationTurn < 0;
    }

    public void activate(int turn) {
        if (turn < 1) {
            throw new IllegalArgumentException("turn must be positive");
        }
        if (!canActivate()) {
            throw new IllegalStateException("Happy Hour can only be activated once");
        }
        activationTurn = turn;
    }

    public boolean isActive(int turn) {
        return activationTurn >= 1 && turn >= activationTurn && turn < activationTurn + 2;
    }

    // Includes the current turn while Happy Hour is active
    public int getRemainingTurns(int turn) {
        return isActive(turn) ? activationTurn + 2 - turn : 0;
    }

    public int priceForOrder(int priceAfterCustomerDiscountCents, int turn) {
        if (priceAfterCustomerDiscountCents < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }
        if (!isActive(turn)) {
            return priceAfterCustomerDiscountCents;
        }
        return Math.toIntExact((priceAfterCustomerDiscountCents * 80L + 50) / 100);
    }
}
