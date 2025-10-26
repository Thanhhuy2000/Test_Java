package model;

import exception.InvalidPlayerException;

public abstract class Player {
    public Object getSaveChance;
    public Object getShotPower;
    protected int id;
    protected String name;
    protected int stamina;
    protected Role role;

    public Player(int id, String name, int stamina, Role role) throws InvalidPlayerException {
        this.id = id;
        this.name = name;
        this.stamina = stamina;
        if (this.stamina < 0 ||  this.stamina > 100) {
            throw new InvalidPlayerException("Invalid stamina");
        }
        this.role = role;
    }

    public abstract void performAction(MatchState state, Player target);
    public boolean canAct() {
        return this.stamina > 0;
    }
    public void rest() {
        this.stamina = Math.min(100, this.stamina + 10);
    }
    public void decreaseStamina10() {
        this.stamina = Math.max(0, this.stamina -= 10);
    }
    public void decreaseStamina5() {
        this.stamina = Math.max(0, this.stamina -= 5);
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public int getStamina() { return this.stamina; }
    public void setStamina (int stamina) { this.stamina = stamina; }
    public Role getRole() { return this.role; }
}
