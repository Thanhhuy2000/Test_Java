package model;

import exception.InvalidPlayerException;

public class Midfielder extends Player {
    private double passAccurecy; // 0 - 1

    public Midfielder(int id, String name, int stamina, double passAccurrecy) throws InvalidPlayerException {
        super(id, name, stamina, Role.MIDFIELDER);
        if (passAccurrecy < 0 || passAccurrecy > 1) {
            throw new InvalidPlayerException("Invalid passAccurrecy");
        }
        this.passAccurecy = passAccurecy;
    }

    @Override
    public void performAction(MatchState state, Player target) {

    }

    public int getId() {return this.id; }
    public String getName() { return this.name; }
    public int  getStamina() { return this.stamina; }
    public double getPassAccurecy() { return this.passAccurecy; }
}
