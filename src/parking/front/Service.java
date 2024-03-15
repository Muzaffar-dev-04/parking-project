package parking.front;

import parking.back.modul.Car;
import parking.back.modul.Park;
import parking.back.modul.User;
import parking.back.repository.CarController;
import parking.back.repository.CarControllerImpl;
import parking.utils.Colors;

import java.util.Scanner;
import java.util.UUID;

public class Service {
    private static final Scanner scannerInt = new Scanner(System.in);
    private static final Scanner scannerStr = new Scanner(System.in);
    private static final CarController carController = CarControllerImpl.getInstance();

    private static final Service instance = new Service();
    private Service(){}

    public static Service getInstance() {
        return instance;
    }
    public boolean carPark(User user, Park parking, int floor, int row, int col){
        if (Park.emptyCeils == 0)   return false;
        System.out.println(Colors.BLUE + "\n< -------------- Let is park your car -------------- >\n" + Colors.RESET);
        parking.displayParking();

        System.out.println();
        System.out.println(Colors.BLUE + "What is your car ?\n1.🚗\n2.🏍️\n3.🚌");
        String type;
        while (true){
            int carType = getIntWithScanner("Choose => ");
            type = switch (carType) {
                case 1 -> "🚗";
                case 2 -> "🏍️";
                case 3 -> "🚌";
                default -> "__";
            };
            if (type.equals("__"))  continue;
            break;
        }
        Car car = new Car(type, parking);
        System.out.println("We have :\n" + floor +" floors\n" + row + " rows on floor\n" + col + " columns in row");
        while (true){
            try {
                int floor1 = getIntWithScanner("Choose floor => ");
                boolean b = checking(floor, 1, 3,"floor");
                parking.displayFloor(floor);
                int row1 = getIntWithScanner("Choose row => ");
                checking(row,1,3,"row");
                parking.displayRow(floor, row);
                int col1 = getIntWithScanner("Choose column => ");
                checking(col,1,5,"column");
                if (!parking.getCar(floor1, row1, col1).getType().equals("__")) {
                    System.out.println(Colors.YELLOW + "Sorry this place is not empty" + Colors.RESET);
                    continue;
                }
                car.setFloor(floor1);
                car.setRow(row1);
                car.setCol(col1);
                parking.setCar(floor1,row1,col1,car);
                parking.displayFloor(floor1);
                System.out.println(Colors.BLUE + "Enter password to your place" + Colors.RESET);
                String password = getStringWithScanner("Enter password => ");
                car.setPassword(password);
                user.setCar(car);
                carController.save(car);
            }catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            user.setCar(car);
            return true;
        }
    }
    public boolean getCar(UUID carUuid,Park parking){
        Car car = carController.findById(carUuid);
        String password = getStringWithScanner("Please enter password of yours car place => ");
        if (!car.getPassword().equals(password)){
            System.out.println(Colors.RED + "No correct password" + Colors.RESET);
            return false;
        }
        parking.remove(car);
        carController.remove(carUuid);
        System.out.println(Colors.PURPLE + "Thank you for using our services" + Colors.RESET);
        return true;
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
    private static boolean checking(int floor, int i, int i1,String str) {
        if (floor < i || floor > i1)    throw new RuntimeException(Colors.RED + "No correct" + str + " you choice" + Colors.RESET);
        return true;
    }
}
