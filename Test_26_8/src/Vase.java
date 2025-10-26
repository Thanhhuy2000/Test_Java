public class Vase extends Item{
    private int height;
    private String material;

    public Vase() {
        super(0, "");
        this.height = 0;
        this.material = "";
    }

    public Vase(int value,  String name, int height, String material) {
        super(value, name);
        this.height = height;
        this.material = material;
    }

    public int getHeight() { return this.height; }
    public String getMaterial() { return this.material; }
    public void setHeight(int height) { this.height = height; }
    public void setMaterial(String material) { this.material = material; }

    public void outputVase() {
        output();

        System.out.println( "Height: " + getHeight() +
                            "\nMaterial: " + getMaterial());
    }

    public void inputVase() {
        System.out.println("Input a new Vase");

        input();

        setHeight(inputInt("Height: "));

        System.out.print("Material: ");
        setMaterial(scanner.nextLine());
    }
}
