package model;

import exception.InvalidContestantException;

public class HeadChef extends Contestant{
    private int cookingSkil; // 0-10

    public HeadChef(int id, String name, int energy, int cookingSkil) throws InvalidContestantException {
        super(id, name, energy, Role.HEAD_CHEF);
        if(cookingSkil < 0 || cookingSkil > 10) {
            throw new InvalidContestantException("Invalid cooking skil value");
        }
        this.cookingSkil = cookingSkil;
    }

    @Override
    public void performTask(CompetitionState state, Dish dish) {
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getEnergy() { return energy; }
    public Role getRole() { return role; }
    public int getCookingSkil() { return cookingSkil; }
}
