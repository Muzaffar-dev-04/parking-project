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

public class Main {

    private static final Scanner scannerInt = new Scanner(System.in);
    private static final Scanner scannerStr = new Scanner(System.in);
    private static final UserController userController = UserControllerImpl.getInstance();
    private static final CarController carController = CarControllerImpl.getInstance();
    private static final Service service = Service.getInstance();
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

        parking = Park.getInstance(floor,row,col);
        parking.fill();
        Entrance entrance = Entrance.getInstance();
        System.out.println(Colors.BLUE + "<------------- Welcome to parking --------------->" + Colors.RESET);

        while (true){
            while (!isLogin) {
                System.out.println(Colors.BLUE + "1.Sign in\n2.Sign up" + Colors.RESET);
                int menu = getIntWithScanner(Colors.YELLOW + "Choose => " + Colors.RESET);

                currentUser = switch (menu) {
                    case 1 -> entrance.signIn();
                    case 2 -> entrance.signUp(parking);
                    default -> null;
                };
                isLogin = (currentUser != null);
            }
            System.out.println(Colors.BLUE + "< -------------- Successfully logged in -------------->" + Colors.RESET);

            //carParking();
            carService();
            isLogin = false;


        }


        //parking.displayParking();


    }

    private static void carService() {
        while (true){
            int menu = displayService();
            switch (menu){
                case 1 -> service.carPark(currentUser,parking,floor,row,col);
                case 2 -> getCar();//service.getCar(currentUser.getCar().getUuid(), parking);
                case 3 -> parking.displayParking();
                case 4 -> {
                    return;
                }
                default -> {
                }
            }
        }
    }

    private static void getCar() {
        if (currentUser.getCar() == null){
            System.out.println(Colors.RED + "You don't have car" + Colors.RESET);
            return;
        }
        service.getCar(currentUser.getCar().getUuid(), parking);
    }

    private static int displayService() {
        System.out.println(Colors.BLUE + "1.Car parking");
        System.out.println("2.Get Car");
        System.out.println("3.Display parking");
        System.out.println("4.exit");
        return getIntWithScanner("Choose => ");
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
                scannerInt.nextLine();
            }
        }
    }
}
