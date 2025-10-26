package model;

import exception.InvalidPlayerException;

public class Forward extends Player {
    private int shotPower; // 0 - 10
    private int tacklePower; // 0 - 5

    public Forward(int id, String name, int stamina,int tacklePower, int shotPower) throws InvalidPlayerException {
        super(id, name, stamina, Role.FORWARDER);
        if (shotPower < 0 || shotPower > 10) {
            throw new InvalidPlayerException("Invalid shotPower");
        }
        if (tacklePower < 0 || tacklePower > 5) {
            throw new InvalidPlayerException("Invalid tacklePower");
        }
        this.shotPower = shotPower;
        this.tacklePower = tacklePower;
    }

    @Override
    public void performAction(MatchState state, Player target) {

    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public int getStamina() { return this.stamina; }
    public int getShotPower() { return this.shotPower; }
    public int getTacklePower() { return this.tacklePower; }
}
