package model;

import exception.InvalidException;

public class Hunter extends Explorer{
    private double combatSkill;

    public Hunter(int id, String name, int stamina, double combatSkill) throws InvalidException {
        super(id, name, stamina, Role.HUNTER);
        this.combatSkill = combatSkill;
        if(combatSkill < 0 || combatSkill > 1) {
            throw new InvalidException("Invalid combat skill");
        }
    }

    @Override
    public void overcomeChallenge(ExpeditionState state, Team team, Challenge challenge) {
        int tmp = (int) (this.combatSkill * 4 + (Math.random() * 20 + 1));
        decreaseStamina();
        for(Explorer explorer : team.getExplorers()) {
            explorer.rest();
        }
        if(challenge.isOvercome(tmp)){
            team.setScore(team.getScore() + challenge.getTraesureValue());
            System.out.println("Nha tham hiem " + this.name + "(Guide) vuot " + challenge.getName() + " -> Thanh cong, thu ve " + challenge.getTraesureValue() + " diem kho bau");
            state.addLog("Luot " + state.getCurrentTurn() + " nha tham hiem " + this.name + " doi " + team.getName() + " vuot thu thach thanh cong");
        }
        else{
            System.out.println("Nha tham hiem " + this.name + "(Guide) vuot " + challenge.getName() + " -> khong thanh cong");
            state.addLog("Luot " + state.getCurrentTurn() + " nha tham hiem " + this.name + " doi " + team.getName() + " khong vuot thu thach thanh cong");
        }
    }

    public double getCombatSkill(){ return this.combatSkill;}
}
