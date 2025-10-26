package model;

import exception.InvalidPlayerException;

public class Goalkeeper extends Player {
    private double saveChance; // 0 - 1

    public Goalkeeper(int id, String name, int stamina, double saveChance) throws InvalidPlayerException {
        super(id, name, stamina, Role.GOALKEEPER);
        if (saveChance < 0 || saveChance > 1) {
            throw new InvalidPlayerException("Invalid saveChance");
        }
        this.saveChance = saveChance;
    }

    @Override
    public void performAction(MatchState state, Player target) {}

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public int getStamina() { return this.stamina; }
    public double getSaveChance() { return this.saveChance; }
}
