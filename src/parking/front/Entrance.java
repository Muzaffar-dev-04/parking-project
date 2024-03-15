package parking.front;

import parking.back.modul.Car;
import parking.back.modul.Park;
import parking.back.modul.User;
import parking.back.repository.UserController;
import parking.back.repository.UserControllerImpl;
import parking.utils.Colors;

import java.util.Scanner;


public class Entrance {
    private static final Scanner scannerInt = new Scanner(System.in);
    private static final Scanner scannerStr = new Scanner(System.in);
    private final UserController userController = UserControllerImpl.getInstance();
    private static final Entrance instance = new Entrance();
    private Entrance(){}

    public static Entrance getInstance() {
        return instance;
    }

    public User signUp(Park parking){
        System.out.println(Colors.BLUE + "<---- Sign Up ---->" + Colors.RESET);
        try {
            String username = getStringWithScanner("Username => ");

            String password = getStringWithScanner("Password => ");

            System.out.println(Colors.BLUE + "What is your car ?\n1.🚗\n2.🏍️\n3.🚌");
            int carType = getIntWithScanner("Choose => ");
            String type = switch (carType) {
                case 1 -> "🚗";
                case 2 -> "🏍️";
                case 3 -> "🚌";
                default -> "__";
            };
            Car car = new Car(type, parking);
            User user = new User(car, username, password);
            userController.addUser(user);
            return user;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User signIn(){
        System.out.println(Colors.BLUE + "<---- Sign In ---->" + Colors.RESET);
        try {
            String username = getStringWithScanner("Username => ");

            String password = getStringWithScanner("Password => ");

            User user = userController.findUserByUsername(username);
            if (user == null || !user.getPassword().equals(password))    throw new RuntimeException(Colors.RED + "Wrong password" + Colors.RESET);
            return user;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    private static String getStringWithScanner(String string) {
        System.out.print(Colors.BLUE + string + Colors.RESET);
        return scannerStr.next();
    }

    private static int getIntWithScanner(String s) {
        while (true){
            try {
                System.out.print(Colors.YELLOW + s + Colors.YELLOW);
                int menu = scannerInt.nextInt();
                return menu;
            } catch (Exception e) {
                System.out.println(Colors.RED + "No correct enter" + Colors.RESET);
            }
        }
    }

}
