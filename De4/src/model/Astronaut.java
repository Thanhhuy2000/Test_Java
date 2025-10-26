package model;

import exception.InvalidException;

import javax.naming.InvalidNameException;

public abstract class Astronaut {
    protected int id; // unique
    protected String name;
    protected int energy; // 0 - 100
    protected Role role;

    public Astronaut (int id, String name, int energy, Role role) throws InvalidException {
        this.id = id;
        this.name = name;
        this.energy = energy;
        this.role = role;
    }

    public abstract void performMission (SpaceState spaceState, Mission mission);

    public boolean canPerform () {
        return this.energy > 0;
    }

    public void rest () {
        setEnergy(Math.min(100, getEnergy() + 10));
    }

    public void decreaseEnergy () {
        setEnergy(Math.max(0, getEnergy() - 10));
    }

    public int getId () { return id; }
    public String getName () { return name; }
    public int getEnergy () { return energy; }
    public void setEnergy (int energy) {
        this.energy = energy;
    }
    public Role getRole () { return role; }
}
