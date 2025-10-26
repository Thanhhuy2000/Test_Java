package model;

import exception.InvalidException;

public class Engineer extends Astronaut{
    private int repairSkill; // 0 - 10

    public Engineer(int id, String name, int energy, int repairSkill) throws InvalidException {
        super(id, name, energy, Role.ENGINEER);
        if (energy < 0 || energy > 100) {
            throw new InvalidException("Invalid energy");
        }
        this.repairSkill = repairSkill;
        if (repairSkill < 0 || repairSkill > 10) {
            throw new InvalidException("Invalid repair skill");
        }
    }

    @Override
    public void performMission (SpaceState state, Mission mission) {
        mission.setDurability(repairSkill * 2);
        mission.setTimeRemaining(mission.getTimeRemaining() - 2);
        decreaseEnergy();
    }

    public int getRepairSkill() { return repairSkill; }
}
