package model;

import exception.InvalidPlayerException;

public class Forward extends Player {
    private int shotPower; // 0 - 10

    public Forward(int id, String name, int stamina, int shotPower) throws InvalidPlayerException {
        super(id, name, stamina, Role.FORWARDER);
        if (shotPower < 0 || shotPower > 10) {
            throw new InvalidPlayerException("Invalid shotPower");
        }
        this.shotPower = shotPower;
    }

    @Override
    public void performAction(MatchState state, Player target) {

    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public int getStamina() { return this.stamina; }
    public int getShotPower() { return this.shotPower; }
}
