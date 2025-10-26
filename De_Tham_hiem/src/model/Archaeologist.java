package model;

import exception.InvalidException;
import java.util.Random;

public class Archaeologist extends Explorer{
    private int knowledge; // 0 - 10

    public Archaeologist(int id, String name, int stamina, int knowledge) throws InvalidException {
        super(id, name, stamina, Role.ARCHAEOLOGIST);
        this.knowledge = knowledge;
        if(knowledge < 0 || knowledge > 10) {
            throw new InvalidException("Invalid knowledge");
        }
    }

    @Override
    public void overcomeChallenge(ExpeditionState state, Team team, Challenge challenge) {
        int tmp = (int)(Math.random() * (60 - 40 + 1) +40);
        decreaseStamina();
        for(Explorer explorer : team.getExplorers()) {
            explorer.rest();
        }
        if(challenge.isOvercome(tmp)){
            team.setScore(team.getScore() + challenge.getTraesureValue() + knowledge * 2);
            System.out.println("Nha tham hiem " + this.name + "(Archaeologist) vuot " + challenge.getName() + " -> Thanh cong, thu ve " + challenge.getTraesureValue() + " diem kho bau");
            state.addLog("Luot " + state.getCurrentTurn() + " nha tham hiem " + this.name + " doi " + team.getName() + " vuot thu thach thanh cong");
        }
        else{
            System.out.println("Nha tham hiem " + this.name + "(Archaeonlogist) vuot " + challenge.getName() + " -> khong thanh cong");
            state.addLog("Luot " + state.getCurrentTurn() + " nha tham hiem " + this.name + " doi " + team.getName() + " khong vuot thu thach thanh cong");
        }
    }

    public int getKnowledge(){ return this.knowledge;}
}
