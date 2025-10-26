package model;

import exception.InvalidPlayerException;

public class Defender extends Player {
    private int tacklePower; // 0 - 10
    private double passAccurecy; // 0 - 0.7

    public Defender(int id, String name, int stamina, int tacklePower, double passAccurecy) throws InvalidPlayerException {
        super(id, name, stamina, Role.DEFENDER);
        if (tacklePower < 0 || tacklePower > 10) {
            throw new InvalidPlayerException("Invalid tacklePower");
        }
        this.tacklePower = tacklePower;
        this.passAccurecy = passAccurecy;
    }

    @Override
    public void performAction(MatchState state, Player target) {

    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public int getStamina() { return this.stamina; }
    public int getTacklePower() { return this.tacklePower; }
    public double getPassAccurecy() { return this.passAccurecy; }
}
