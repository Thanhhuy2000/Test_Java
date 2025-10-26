package model;

import exception.InvalidContestantException;

public class SousChef extends Contestant{
    private int prepSpeed; // 0-10

    public SousChef(int id, String name, int energy, int prepSpeed) throws InvalidContestantException {
        super(id, name, energy, Role.SOUS_CHEF);
        if(prepSpeed < 0 || prepSpeed > 10) {
            throw new InvalidContestantException("Invalid prepSpeed");
        }
        this.prepSpeed = prepSpeed;
    }

    @Override
    public void performTask(CompetitionState state, Dish dish) {

    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getEnergy() { return energy; }
    public Role getRole() { return role; }
    public int getPrepSpeed() { return prepSpeed; }
}
