package model;

import exception.InvalidException;

public abstract class Staff {
    protected int id; // unique
    protected String name;
    protected int stamina; // 0 - 100
    protected Role role;

    public Staff(int id, String name, int stamina, Role role) throws InvalidException {
        this.id = id;
        this.name = name;
        this.stamina = stamina;
        if(this.stamina < 0 || this.stamina > 100) {
            throw new InvalidException("Invalid stamina");
        }
        this.role = role;
    }

    public abstract void handleDinosaur(ParkState state, Team team, Dinosaur dinosaur);

    public boolean canWork() {
        return this.stamina > 0;
    }

    public void rest() {
        this.stamina = Math.min(100, this.stamina + 5);
    }

    public void decreaseStamina() {
        this.stamina = Math.max(0, this.stamina - 20);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getStamina() { return stamina; }
    public Role getRole() { return role; }
}
