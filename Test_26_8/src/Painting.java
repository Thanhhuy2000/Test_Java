import java.util.Scanner;

public class Painting extends Item{
    private int height;
    private int width;
    private boolean isWaterColour;
    private boolean isFramed;
    private Scanner scanner = new Scanner(System.in);

    public Painting() {
        super(0, "");
        this.height = 0;
        this.width = 0;
        this.isWaterColour = false;
        this.isFramed = false;
    }

    public Painting(int value,  String name) {
        super(value, name);
        this.height = height;
        this.width = width;
        this.isWaterColour = false;
        this.isFramed = false;
    }

    public int getHeight() { return this.height; }
    public int getWidth() { return this.width; }
    public void setHeight(int height) { this.height = height; }
    public void setWidth(int width) { this.width = width; }

    public void outputPainting() {
        output();

        System.out.println( "Height: " + getHeight() +
                            "\nWidth: " + getWidth());

        if (isWaterColour) {
            System.out.println("Painting water");

        }
        if (isFramed) {
            System.out.println("Painting framed");
        }
    }

    public boolean inputYesNo(String message) {
        while (true) {
            System.out.print(message);
            String choice = scanner.nextLine().trim();
            if (choice.equalsIgnoreCase("Y")) {
                return true;
            } else if (choice.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Please enter Y or N!");
            }
        }
    }

    public void inputPainting() {
        System.out.println("Input a new Painting");

        input();

        setHeight(inputInt("Height: "));
        setWidth(inputInt("Width: "));

        isWaterColour = inputYesNo("Painting water?(Y/N): ");
        isFramed = inputYesNo("Painting framed?(Y/N): ");
    }
}
