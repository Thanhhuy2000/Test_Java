import java.util.Scanner;

public class Item {
    protected int value;
    protected String name;
    public Scanner scanner = new Scanner(System.in);

    public Item(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void output() {
        System.out.println( "Name: " + this.name +
                            "\nValue: " + this.value);
    }

    protected int inputInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public void input() {
        System.out.print("Name: ");
        setName(scanner.nextLine());

        setValue(inputInt("Value: "));
    }
}
