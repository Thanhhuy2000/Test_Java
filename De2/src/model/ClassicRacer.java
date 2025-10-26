package model;

public class ClassicRacer extends Racer {
    private int control; // 0-10

    public ClassicRacer(String id, String name, int fuel, int control) {
        super(id, name, fuel, VehicleType.CLASSIC);
        this.control = control;
    }

    @Override
    public boolean canNavigate(Obstacle obstacle) {
        return fuel > 0;
    }

    @Override
    public boolean navigateObstacle(RaceState state, Obstacle obstacle) {
        double success = (double) control / obstacle.getDifficulty();
        fuel -= 10; // Giảm 10 fuel mỗi lần vượt
        if (fuel < 0) fuel = 0;
        return Math.random() < success;
    }

    @Override
    public void refuel() {
        this.fuel = 100;
    }

    public int getControl() { return control; }
} 