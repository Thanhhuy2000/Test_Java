package model;

import exception.InvalidException;

public class Ranger extends Staff{
    private int controlSkill; // 0 - 10

    public Ranger(int id, String name, int stamina, int controlSkill) throws InvalidException {
        super(id, name, stamina, Role.RANGER);
        this.controlSkill = controlSkill;
        if(this.controlSkill < 0 || this.controlSkill > 10) {
            throw new InvalidException("Invalid control skill");
        }
    }

    @Override
    public void handleDinosaur(ParkState state, Team team, Dinosaur dinosaur){
        int tmp = Math.max(0, dinosaur.getDangerLevel() - controlSkill);
        dinosaur.setDangerLevel(tmp);
        decreaseStamina();
        if(dinosaur.isStable()){
            team.setSaveScore(team.getSaveScore() + 1);
        }
        System.out.println("Nhan vien " + name + " (Ranger) cham soc " + dinosaur.getName() + " -> Giam do nguy hiem xuong " + controlSkill);
        state.addLog("Luot " + state.getCurrentTurn() + " nhan vien " +  name + " (Ranger) team " + team.getName() + " cham soc " + dinosaur.getName());
    }

    public int getControlSkill() { return this.controlSkill; }
}
