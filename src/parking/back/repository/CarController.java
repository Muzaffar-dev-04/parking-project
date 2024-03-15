package parking.back.repository;

import parking.back.modul.Car;

import java.util.List;

public interface CarController {
    boolean save(Car car);
    boolean remove(Car car);
    List<Car> getAll();
}
