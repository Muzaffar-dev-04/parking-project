package parking.back.repository;

import parking.back.modul.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarControllerImpl implements CarController {
    //private static List<Car> cars = new ArrayList<>();
    private static final CarController instance = new CarControllerImpl();
    private CarControllerImpl(){}

    public static CarController getInstance() {
        return instance;
    }

    @Override
    public List<Car> getAll() {
        List<Car> cars = read();
        return cars;
    }

    @Override
    public boolean save(Car car) {
        List<Car> cars = read();
        cars.add(car);
        write(cars);
        return true;
    }

    @Override
    public boolean remove(UUID uuid) {
        List<Car> cars = read();
        Car car = findById(uuid);
        cars.remove(car);
        write(cars);
        return true;
    }

    @Override
    public Car findById(UUID uuid) {
        List<Car> cars = read();
        for (Car car : cars) {
            if (car.getUuid().equals(uuid))     return car;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private List<Car> read(){
        File file = new File("repository/cars.txt");
        if (file.length() == 0)     return new ArrayList<>();
        try (
                FileInputStream fileInputStream = new FileInputStream("repository/cars.txt");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ){
            List<Car> cars = (List<Car>) objectInputStream.readObject();
            return cars;
            //return (List<Car>) objectInputStream.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void write(List<Car> cars){
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
