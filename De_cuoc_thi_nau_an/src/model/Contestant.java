package model;

import exception.InvalidContestantException;

public abstract class Contestant {
    protected int id; // unique
    protected String name;
    protected int energy; // 0-100
    protected Role role;

    public Contestant(int id, String name, int energy, Role role) throws InvalidContestantException {
        this.id = id;
        this.name = name;
        this.energy = energy;
        if(this.energy < 0 || this.energy > 100) {
            throw new InvalidContestantException("Invalid energy value");
        }
        this.role = role;
    }

    public abstract void performTask(CompetitionState state, Dish dish);
    public boolean canPerform () {
        return this.energy > 0;
    }
    public void rest() {
        this.energy = Math.min(100, this.energy += 10);
    }
    public void decreaseEnergy() {
        this.energy = Math.max(0, this.energy -= 10);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getEnergy() { return energy; }
    public Role getRole() { return role; }
}
