package model;

import exception.InvalidPlayerException;

public class Goalkeeper extends Player {
    private double saveChance; // 0 - 1
    private double passAccurecy; // 0 - 0.5

    public Goalkeeper(int id, String name, int stamina, double saveChance, double passAccurecy) throws InvalidPlayerException {
        super(id, name, stamina, Role.GOALKEEPER);
        if (saveChance < 0 || saveChance > 1) {
            throw new InvalidPlayerException("Invalid saveChance");
        }
        if (passAccurecy < 0 || passAccurecy > 0.5) {
            throw new InvalidPlayerException("Invalid passAccurecy");
        }
        this.saveChance = saveChance;
        this.passAccurecy = passAccurecy;
    }

    @Override
    public void performAction(MatchState state, Player target) {}

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public int getStamina() { return this.stamina; }
    public double getSaveChance() { return this.saveChance; }
    public double getPassAccurecy() { return this.passAccurecy; }
}
