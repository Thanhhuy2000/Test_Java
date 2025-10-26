package model;

import exception.InvalidException;

public class Scientist extends Staff{
    private int researchSkill; // 0 - 10

    public Scientist(int id, String name, int stamina, int researchSkill) throws InvalidException {
        super(id, name, stamina, Role.SCIENTIST);
        this.researchSkill = researchSkill;
        if(this.researchSkill < 0 || this.researchSkill > 10) {
            throw new InvalidException("Invalid research skill");
        }
    }

    @Override
    public void handleDinosaur(ParkState state, Team team, Dinosaur dinosaur){
        dinosaur.setHealth(dinosaur.getHealth() + researchSkill);
        decreaseStamina();
        if(dinosaur.isStable()){
            team.setSaveScore(team.getSaveScore() + 1);
        }
        System.out.println("Nhan vien " + name + " (Scientist) cham soc " + dinosaur.getName() + " -> Tang suc khoe len " + researchSkill);
        state.addLog("Luot " + state.getCurrentTurn() + " nhan vien " +  name + " (Scientist) team " + team.getName() + " cham soc " + dinosaur.getName());
    }

    public int getResearchSkill() { return this.researchSkill; }
}
