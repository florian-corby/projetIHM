package silent_in_space.model.Items;

import silent_in_space.model.Characters.Actor;
import silent_in_space.model.Characters.Player;
import silent_in_space.model.Containers.*;
import silent_in_space.model.Events.Event;
import silent_in_space.model.Game.Message;
import silent_in_space.model.Utils.Scalar2D;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Computer extends Item implements Serializable {

    private final Inventory FILES;
    private Event EVENT;

    public Computer(String description, String tag)
    {
        super(tag, description, new Scalar2D(0, 0));
        this.FILES = new Inventory();
        this.EVENT = null;
    }

    public Computer(String description, String tag, Event event)
    {
        this(description, tag);
        this.EVENT = event;
    }

    public Computer(String description, String tag, Scalar2D scalar2D)
    {
        super(tag, description, scalar2D);
        this.FILES = new Inventory();
        this.EVENT = null;
    }

    public Computer(String description, String tag, Event event, Scalar2D scalar2D)
    {
        super(tag, description, scalar2D);
        this.FILES = new Inventory();
        this.EVENT = event;
    }

    public void addFile(File f){
        this.FILES.addItem(f);
    }

    public Event getEVENT() { return this.EVENT; }
    public Inventory getFILES() { return this.FILES; }

    @Override
    public void isUsed(UsableBy u) {

        if(u instanceof Player) {
            Player player = (Player) u;
            System.out.println("Welcome to the lab Computer. You can consult lab files, or generate a Pass. Please input a command :");

            boolean quit = false;
            while (!quit) {
                System.out.println("\n=== AVAILABLE COMMANDS ===");
                System.out.println("\t:> open : show a file");
                System.out.println("\t:> print : print a file");

                if(this.EVENT != null)
                    System.out.println("\t:> " + this.EVENT.getTag() + " : " + this.EVENT.getDescription());

                System.out.println("\t:> quit");

                quit = playerInput(player);
            }
        }

        else
            System.out.println("Error :> This object can't use the computer");
    }

    public boolean playerInput(Player player) {

        Scanner sc = new Scanner(System.in);
        String userChoice = sc.nextLine();

        if (this.EVENT != null && userChoice.equals(this.EVENT.getTag())) {
            this.EVENT.getE().raise(player);
            return false;
        }

        else {
            try {
                switch (userChoice) {
                    case "open":
                        System.out.println("\nYou chose to open a file.");
                        System.out.println("\n=== AVAILABLE FILES ===");
                        this.FILES.showItems();
                        Scanner sc0 = new Scanner(System.in);
                        String choice = sc0.nextLine();

                        try {
                            this.FILES.getItem(choice).isUsed(player);
                            return false;
                        } catch (NullPointerException e) {
                            System.out.println("\nThis file doesn't exist");
                            return false;
                        }

                    case "print":
                        System.out.println("\nYou chose to print a file.");
                        System.out.println("\n=== AVAILABLE FILES ===");
                        this.FILES.showItems();
                        Scanner sc1 = new Scanner(System.in);
                        String print = sc1.nextLine();
                        printFile(print, player);
                        return false;

                    case "quit":
                        return true;

                    default:
                        System.out.println("\nPlease enter a valid input");
                        return false;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nPlease enter a valid input");
                return false;
            }
        }
    }

    public void printFile(String tag, UsableBy a) {

        if(a instanceof Actor) {

            Actor player = (Actor) a;

            try {
                File file = (File) this.FILES.getItem(tag);
                player.getInventory().addItem(file.getCopy());
                Message.sendGameMessage("Now you have a copy of the " + file.getTag() + " in your inventory");
            }

            catch(NullPointerException | ClassCastException e) {
                Message.sendGameMessage("Error :> This file doesn't exist");
            }
        }
    }
}
