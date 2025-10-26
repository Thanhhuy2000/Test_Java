package model;

import exception.InvalidException;

public class Scientist extends Astronaut {
    private int researchLever; // 0 - 10

    public Scientist(int id, String name, int energy, int researchLever) throws InvalidException {
        super(id, name, energy, Role.SCIENTIST);
        if (energy < 0 || energy > 100) {
            throw new InvalidException("Invalid energy");
        }
        this.researchLever = researchLever;
        if (researchLever < 0 || researchLever > 10) {
            throw new InvalidException("Invalid research skill");
        }
    }

    @Override
    public void performMission (SpaceState spaceState, Mission mission) {
        mission.setDurability(researchLever + (int) (Math.random() * 10));
        mission.setTimeRemaining(mission.getTimeRemaining() - 2);
        decreaseEnergy();
    }

    public int getResearchLever() { return researchLever; }
}
