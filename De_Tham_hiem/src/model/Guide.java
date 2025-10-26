package model;

import exception.InvalidException;

public class Guide extends Explorer{
    private int navigation; // 0 - 10

    public Guide(int id, String name, int stamina, int navigation) throws InvalidException {
        super(id, name, stamina, Role.GUDIE);
        this.navigation = navigation;
        if(navigation < 0 || navigation > 10) {
            throw new InvalidException("Invalid navigation");
        }
    }

    @Override
    public void overcomeChallenge(ExpeditionState state, Team team, Challenge challenge) {
        int tmp = (int) (this.navigation * (Math.random() * 10 + 1));
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

    public int getNavigation(){ return this.navigation;}
}
