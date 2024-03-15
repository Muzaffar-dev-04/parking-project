package parking.back.modul;

import parking.back.repository.CarController;
import parking.back.repository.CarControllerImpl;
import parking.utils.Colors;

import java.util.List;

public class Park {
    private Car[][][] park;
    private int emptyCeils;
    public Park(int floor,int row,int col) {
        this.park = new Car[floor][row][col];
        for (int i = 0; i < floor; i++) {
            for (int j = 0; j < row; j++) {
                for (int k = 0; k < col; k++) {
                    park[i][j][k] = new Car("__",this);
                    emptyCeils++;
                }
            }
        }
    }

    public Car getCar(int floor,int row,int col){
        if (park[--floor][--row][--col].getType().equals("-"))    throw new RuntimeException("There is no car🔴🔴");
        return park[floor][row][col];
    }
    public void setCar(int floor,int row,int col,Car car){
        floor--;
        row--;
        col--;
        park[floor][row][col] = car;
        emptyCeils--;
    }

    public void displayRow(int floor,int row){
        floor--;
        row--;
        System.out.print(Colors.BLUE + "   |  " + Colors.RESET);
        for (int k = 0; k < park[floor][row].length; k++) {
            System.out.print(park[floor][row][k].getType() + "   ");
        }
        System.out.println(Colors.BLUE + "|" + Colors.RESET);
    }
    public void displayFloor(int floor){
        floor--;
        for (int j = 0; j < park[floor].length; j++) {
            displayRow(floor + 1,j + 1);
        }
    }
    public void displayParking(){
        StringBuilder stringBuilder = new StringBuilder("  < ");
        extraM1(stringBuilder);
        stringBuilder.append(" Parking ");
        extraM1(stringBuilder);
        System.out.println(Colors.BLUE + stringBuilder + " >" + Colors.RESET);
        for (int i = 0; i < park.length; i++) {
            System.out.println();
            displayFloor(i + 1);
        }
    }
    private void extraM1(StringBuilder stringBuilder){
        for (int i = 0; i < park[0][0].length/2; i++) {
            stringBuilder.append("----");
        }
    }

    public void fill() {
        CarController carController = CarControllerImpl.getInstance();
        List<Car> cars = carController.getAll();
        for (Car car : cars) {
            if (car.getFloor() == 0 || car.getRow() == 0 || car.getCol() == 0) {
                System.out.println(car);
                continue;
            }
            park[car.getFloor() - 1][car.getRow() - 1][car.getCol() - 1] = car;
        }
    }
}

