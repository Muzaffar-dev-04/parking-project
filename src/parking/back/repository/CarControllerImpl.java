package parking.back.repository;

import parking.back.modul.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CarControllerImpl implements CarController {
    private static List<Car> cars = new ArrayList<>();
    private static final CarController instance = new CarControllerImpl();
    private CarControllerImpl(){}

    public static CarController getInstance() {
        return instance;
    }

    @Override
    public List<Car> getAll() {
        cars = read();
        return cars;
    }

    @Override
    public boolean save(Car car) {
        cars = read();
        cars.add(car);
        write();
        return true;
    }

    @Override
    public boolean remove(Car car) {
        cars = read();
        cars.remove(car);
        write();
        return true;
    }

    @SuppressWarnings("unchecked")
    private List<Car> read(){
        File file = new File("repository/cars.txt");
        if (file.length() == 0)     return new ArrayList<>();
        try (
                FileInputStream fileInputStream = new FileInputStream("repository/cars.txt");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ){
            return (List<Car>) objectInputStream.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void write(){
        try (
                FileOutputStream fileOutputStream = new FileOutputStream("repository/cars.txt");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ){
            objectOutputStream.writeObject(cars);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
