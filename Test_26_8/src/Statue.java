import java.util.Scanner;

public class Statue extends Item {
    private int weight;
    private String colour;
    private Scanner scanner = new Scanner(System.in);

    public Statue() {
        super(0, "");
        this.weight = 0;
        this.colour = "";
    }

    public Statue(int value,  String name) {
        super(value, "Statue");
        this.weight = weight;
        this.colour = colour;
    }

    public int getWeight() { return this.weight; }
    public String getColour() { return this.colour; }
    public void setWeight(int weight) { this.weight = weight; }
    public void setColour(String colour) { this.colour = colour; }

    public void outputStatue() {
        output();

        System.out.println( "Weight: " + getWeight() +
                            "\nColour: " + getColour());
    }

    public void inputStatue() {
        System.out.println("Input a new Statue");

        input();

        setWeight(inputInt("Weight: "));

        System.out.print("Colour: ");
        setColour(scanner.nextLine());
    }
}
