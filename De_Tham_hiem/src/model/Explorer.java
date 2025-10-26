package model;

import exception.InvalidException;

public abstract class Explorer {
    protected int id; // unique
    protected String name;
    protected int stamina; // 0 - 100
    protected Role role;

    public Explorer(int id, String name, int stamina, Role role) throws InvalidException {
        this.id = id;
        this.name = name;
        this.stamina = stamina;
        if(stamina < 0 || stamina > 100) {
            throw new InvalidException("Invalid stamina");
        }
        this.role = role;
    }

    public abstract void overcomeChallenge(ExpeditionState state, Team team, Challenge challenge);

    public boolean canExplorer() {
        return stamina > 0;
    }

    public void rest() {
        Math.min(100, stamina += 5);
    }

    public void decreaseStamina() {
        Math.max(0, stamina -= 20);
    }

    public int getStamina() { return this.stamina; }
    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public Role getRole() { return this.role; }
}
