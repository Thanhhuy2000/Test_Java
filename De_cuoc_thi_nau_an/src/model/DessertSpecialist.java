package model;

import exception.InvalidContestantException;

public class DessertSpecialist extends Contestant{
    private double creativity; // 0-1

    public DessertSpecialist(int id, String name, int energy, double creativity) throws InvalidContestantException {
        super(id, name, energy, Role.DESSERT_SPECIALIST);
        if(creativity < 0 || creativity > 1) {
            throw new InvalidContestantException("Invalid creativity");
        }
        this.creativity = creativity;
    }

    @Override
    public void performTask(CompetitionState state, Dish dish) {

    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getEnergy() { return energy; }
    public Role getRole() { return role; }
    public double getCreativity() { return creativity; }
}
