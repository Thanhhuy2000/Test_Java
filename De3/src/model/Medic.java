package model;

import exception.InvalidException;

public class Medic extends Staff{
    private double healRate; // 0 - 1

    public Medic(int id, String name, int stamina, double healRate) throws InvalidException {
        super(id, name, stamina, Role.MEDIC);
        this.healRate = healRate;
        if(this.healRate < 0 || this.healRate > 1) {
            throw new InvalidException("Invalid heal rate");
        }
    }

    @Override
    public void handleDinosaur(ParkState state, Team team, Dinosaur dinosaur){
        dinosaur.setHealth(dinosaur.getHealth() + (int)(healRate * 10));
        decreaseStamina();
        if(dinosaur.isStable()){
            team.setSaveScore(team.getSaveScore() + 1);
        }
        System.out.println("Nhan vien " + name + " (Medic) cham soc " + dinosaur.getName() + " -> Tang suc khoe len " + (int)(healRate * 10));
        state.addLog("Luot " + state.getCurrentTurn() + " nhan vien " +  name + " (Medic) team " + team.getName() + " cham soc " + dinosaur.getName());
    }

    public double getHealRate() { return this.healRate; }
}
