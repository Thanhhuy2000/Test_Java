package model;

import exception.InvalidPlayerException;

public class Midfielder extends Player {
    private int tacklePower; // 0 - 7
    private double passAccurecy; // 0 - 1
    private int shotPower; // 0 - 7;

    public Midfielder(int id, String name, int stamina, int tacklePower, double passAccurrecy, int shotPower) throws InvalidPlayerException {
        super(id, name, stamina, Role.MIDFIELDER);
        if (tacklePower < 0 || tacklePower > 7) {
            throw new InvalidPlayerException("Invalid tacklePower");
        }
        if (passAccurrecy < 0 || passAccurrecy > 1) {
            throw new InvalidPlayerException("Invalid passAccurrecy");
        }
        if (shotPower < 0 || shotPower > 7) {
            throw new InvalidPlayerException("Invalid shotPower");
        }
        this.tacklePower = tacklePower;
        this.passAccurecy = passAccurecy;
        this.shotPower = shotPower;
    }

    @Override
    public void performAction(MatchState state, Player target) {

    }

    public int getId() {return this.id; }
    public String getName() { return this.name; }
    public int  getStamina() { return this.stamina; }
    public int getTacklePower() { return this.tacklePower; }
    public double getPassAccurecy() { return this.passAccurecy; }
    public int getShotPower() { return this.shotPower; }
}
