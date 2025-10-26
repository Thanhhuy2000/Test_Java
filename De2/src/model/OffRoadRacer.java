package model;

public class OffRoadRacer extends Racer {
    private int durability; // 0-10

    public OffRoadRacer(String id, String name, int fuel, int durability) {
        super(id, name, fuel, VehicleType.OFF_ROAD);
        this.durability = durability;
    }

    @Override
    public boolean canNavigate(Obstacle obstacle) {
        return fuel > 0;
    }

    @Override
    public boolean navigateObstacle(RaceState state, Obstacle obstacle) {
        double success = (double) durability / obstacle.getDifficulty();
        fuel -= 15; // Giảm 15 fuel mỗi lần vượt
        if (fuel < 0) fuel = 0;
        return Math.random() < success;
    }

    @Override
    public void refuel() {
        this.fuel = 100;
    }

    public int getDurability() { return durability; }
} 