import exception.*;
import service.Simulation;

public class Main {
    public static void main(String[] args) throws InvalidException {
        Simulation simulation = new Simulation();
        try {
            simulation.setup();
            simulation.run();
        }
        catch (InvalidException e) {
            System.out.println("Loi " + e.getMessage());
        }
    }
}
