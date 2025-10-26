package model;

import exception.InvalidException;

public class Pilot extends Astronaut{
    private double navigationSkill; // 0 - 1

    public Pilot (int id, String name, int energy, double navigationSkill) throws InvalidException {
        super(id, name, energy, Role.PILOT);
        if (energy < 0 || energy > 100) {
            throw new InvalidException("Invalid energy");
        }
        this.navigationSkill = navigationSkill;
        if (navigationSkill < 0 || navigationSkill > 1) {
            throw new InvalidException("Invalid navigation skill");
        }
    }

    @Override
    public void performMission (SpaceState spaceState, Mission mission) {
        mission.setTimeRemaining(mission.getTimeRemaining() - 4);
        decreaseEnergy();
    }

    public double getNavigationSkill() { return navigationSkill; }
}
