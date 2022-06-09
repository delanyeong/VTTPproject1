package project.core;

import java.io.Console;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class ShoppingCart {

    public static void main(String[] args) {

        File directory = new File("./src/main/java/project/core/shoppingcart");
        int fileCount = directory.list().length;

        // Scanner
        Scanner open = new Scanner(System.in);
        // There are n carts in shoppingcart directory
        System.out.printf("There are %d carts in shoppingcart directory\n", fileCount);

        // this is where you type load name
        String input = open.nextLine();

        String[] firstline = input.split(" ");
        String fname = firstline[1];

        if (firstline[0].equals("load")) {

            System.out.println(fname + " shopping cart loaded");

            try {

                FileInputStream fileStream = new FileInputStream(
                        "./src/main/java/project/core/shoppingcart/" + fname + ".cart");

                ObjectInputStream is = new ObjectInputStream(fileStream);

                Cart fredCart = (Cart) is.readObject();

                is.close();

                System.out.println(fredCart);

                int delIndex;
                boolean stop = false;

                while (!stop) {
                    Console cons = System.console();
                    String input2 = cons.readLine();

                    String[] terms = input2.split(" ");
                    String cmd = terms[0];
                    switch (cmd) {
                        case "add":
                            for (int i = 1; i < terms.length; i++) {
                                boolean found = false;
                                for (int j = 0; j < fredCart.getCart().size(); j++) {
                                    if (terms[i].equals(fredCart.getCart().get(j))) {
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    fredCart.getCart().add(terms[i]);
                                    System.out.printf("Added %s to cart\n", terms[i]);
                                }
                            }
                            break;

                        case "list":
                            if (fredCart.getCart().size() > 0) {
                                for (int i = 0; i < fredCart.getCart().size(); i++) {
                                    System.out.printf("%d. %s\n", (i + 1), fredCart.getCart().get(i));
                                }
                            } else {
                                System.out.println("Your cart is empty");
                            }
                            break;

                        case "del":
                            if (terms.length < 2) {
                                System.err.println("Please provide an index to delete");
                            } else {
                                delIndex = Integer.parseInt(terms[1]) - 1;
                                if (delIndex < fredCart.getCart().size()) {
                                    System.out.printf("Deleted %s from cart\n", fredCart.getCart().get(delIndex));
                                    fredCart.getCart().remove(delIndex);
                                } else {
                                    System.err.println("No such item");
                                }
                            }
                            break;

                        case "end":
                            stop = true;
                            break;

                        default:
                    }
                }

                FileOutputStream outStream = new FileOutputStream("fred.cart");
                ObjectOutputStream os = new ObjectOutputStream(outStream);

                os.writeObject(fredCart);

                os.close();

                open.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            System.out.println("try again");
        }
    }
}
