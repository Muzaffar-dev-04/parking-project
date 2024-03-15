package parking.front;

import parking.back.modul.Car;
import parking.back.modul.Park;
import parking.back.modul.User;
import parking.back.repository.CarController;
import parking.back.repository.CarControllerImpl;
import parking.back.repository.UserController;
import parking.back.repository.UserControllerImpl;
import parking.utils.Colors;

import java.util.Scanner;

public class Service {

    private static Scanner scannerInt = new Scanner(System.in);
    private static Scanner scannerStr = new Scanner(System.in);
    private static UserController userController = UserControllerImpl.getInstance();
    private static CarController carController = CarControllerImpl.getInstance();
    private static boolean isLogin = false;
    private static User currentUser;
    private static Park parking;
    private static int floor;
    private static int row;
    private static int col;
    public static void main(String[] args) {


        floor = 3;//getIntWithScanner("How many floors in parking => ");
        row = 3;//getIntWithScanner("How many rows in floor => ");
        col = 5;//getIntWithScanner("How many cols in row => ");

        parking = new Park(floor, row, col);
        parking.fill();
        Entrance entrance = Entrance.getInstance();
        System.out.println(Colors.BLUE + "<------------- Welcome to parking --------------->" + Colors.RESET);

        while (true){
            while (!isLogin) {
                System.out.println(Colors.BLUE + "1.Sign in\n2.Sign up" + Colors.RESET);
                int menu = getIntWithScanner(Colors.YELLOW + "Choose => " + Colors.RESET);
                switch (menu){
                    case 1 -> signIn();
                    case 2 -> signUp();
                    default -> {
                        continue;
                    }
                }

                /*currentUser = switch (menu) {
                    case 1 -> entrance.signIn();
                    case 2 -> entrance.signUp(parking);
                    default -> null;
                };
                isLogin = (currentUser != null);*/
            }
            System.out.println(Colors.BLUE + "< -------------- Successfully logged in -------------->" + Colors.RESET);
            carParking();
            isLogin = false;
        }

        //parking.displayParking();


    }

    private static void carParking() {
        System.out.println(Colors.BLUE + "\n< -------------- Let is park your car -------------- >\n" + Colors.RESET);
        parking.displayParking();
        System.out.println("We have :\n" + floor +" floors\n" + row + " rows on floor\n" + col + " columns in row");
        while (true){
            try {
                int floor = getIntWithScanner("Choose floor => ");
                boolean b = checking(floor, 1, 3,"floor");
                parking.displayFloor(floor);
                int row = getIntWithScanner("Choose row => ");
                checking(row,1,3,"row");
                parking.displayRow(floor, row);
                int col = getIntWithScanner("Choose column => ");
                checking(col,1,5,"column");
                if (!parking.getCar(floor, row, col).getType().equals("__")) {
                    System.out.println(Colors.YELLOW + "Sorry this place is not empty" + Colors.RESET);
                    continue;
                }
                currentUser.getCar().setFloor(floor);
                currentUser.getCar().setRow(row);
                currentUser.getCar().setCol(col);
                parking.setCar(floor,row,col,currentUser.getCar());
                parking.displayFloor(floor);
                carController.save(currentUser.getCar());
            }catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            return;
        }
    }

    private static boolean checking(int floor, int i, int i1,String str) {
        if (floor < i || floor > i1)    throw new RuntimeException(Colors.RED + "No correct" + str + " you choice" + Colors.RESET);
        return true;
    }

    private static void signUp() {
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
            currentUser = user;
            isLogin = true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    private static void signIn() {
        System.out.println(Colors.BLUE + "<---- Sign In ---->" + Colors.RESET);
        try {
            String username = getStringWithScanner("Username => ");

            String password = getStringWithScanner("Password => ");

            User user = userController.findUserByUsername(username);
            if (user == null || !user.getPassword().equals(password))    throw new RuntimeException(Colors.RED + "Wrong password" + Colors.RESET);
            currentUser = user;
            isLogin = true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
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
