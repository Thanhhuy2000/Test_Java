import exception.*;
import service.Simulation;

public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        try {
            simulation.setup();
            simulation.run();
        }
        catch (InvalidContestantException e) {
            System.out.println("Loi " + e.getMessage());
        }
    }
}
