package model;

public class SportRacer extends Racer {
    private int speed; // 0-10

    public SportRacer(String id, String name, int fuel, int speed) {
        super(id, name, fuel, VehicleType.SPORT);
        this.speed = speed;
    }

    @Override
    public boolean canNavigate(Obstacle obstacle) {
        return fuel > 0;
    }

    @Override
    public boolean navigateObstacle(RaceState state, Obstacle obstacle) {
        double success = (double) speed / obstacle.getDifficulty();
        fuel -= 20; // Giảm 20 fuel mỗi lần vượt
        if (fuel < 0) fuel = 0;
        return Math.random() < success;
    }

    @Override
    public void refuel() {
        this.fuel = 100;
    }

    public int getSpeed() { return speed; }
} 