import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Item> items = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean check = true;

        while (check) {
            System.out.println( "1.Input a new Vase\n" +
                                "2.Input a new Painting\n" +
                                "3.Input a new Statue\n" +
                                "4.Output all items\n" +
                                "5.Output all Vase\n" +
                                "6.Output all Painting\n" +
                                "7.Output all Statue\n" +
                                "8.Quit");
            System.out.print("Choice: ");
            int choice = Integer.parseInt(scanner.nextLine());
            System.out.println();

            switch (choice) {
                case 1:
                    Vase v = new Vase();
                    v.inputVase();
                    items.add(v);
                    break;
                case 2:
                    Painting p = new Painting();
                    p.inputPainting();
                    items.add(p);
                    break;
                case 3:
                    Statue s = new Statue();
                    s.inputStatue();
                    items.add(s);
                    break;
                case 4:
                    System.out.println("All Items\n");
                    for(Item i : items) {
                        if(i instanceof Vase) {
                            System.out.println("Item: Vase");
                            ((Vase) i).outputVase();
                        }
                        else if(i instanceof Painting) {
                            System.out.println("Item: Painting");
                            ((Painting) i).outputPainting();
                        }
                        else if(i instanceof Statue) {
                            System.out.println("Item: Statue");
                            ((Statue) i).outputStatue();
                        }
                        System.out.println();
                    }
                    break;
                case 5:
                    System.out.println("All Vases");
                    for(Item i : items) {
                        if(i instanceof Vase) {
                            ((Vase) i).outputVase();
                        }
                        System.out.println();
                    }
                    break;
                case 6:
                    System.out.println("All Paintings");
                    for(Item i : items) {
                        if(i instanceof Painting) {
                            ((Painting) i).outputPainting();
                        }
                        System.out.println();
                    }
                    break;
                case 7:
                    System.out.println("All Statues");
                    for(Item i : items) {
                        if(i instanceof Statue) {
                            ((Statue) i).outputStatue();
                        }
                        System.out.println();
                    }
                    break;
                case 8:
                    check = false;
                    System.out.println("End");
                    break;
                default:
                    System.out.println("Error, try again");
                    break;
            }
            System.out.println();
        }
    }
}
