package model;

public abstract class Racer {
    protected String id;
    protected String name;
    protected int fuel;
    protected VehicleType vehicleType;
    protected int obstaclesPassedCount = 0;

    public Racer(String id, String name, int fuel, VehicleType vehicleType) {
        this.id = id;
        this.name = name;
        this.fuel = fuel;
        this.vehicleType = vehicleType;
    }

    public abstract boolean canNavigate(Obstacle obstacle);
    public abstract boolean navigateObstacle(RaceState state, Obstacle obstacle);
    public abstract void refuel();

    public String getName() { return name; }
    public int getFuel() { return fuel; }
    public VehicleType getVehicleType() { return vehicleType; }
    public String getId() { return id; }
    public void setFuel(int fuel) { this.fuel = fuel; }
    public int getObstaclesPassedCount() { return obstaclesPassedCount; }
    public void incrementObstaclesPassed() { obstaclesPassedCount++; }
} 