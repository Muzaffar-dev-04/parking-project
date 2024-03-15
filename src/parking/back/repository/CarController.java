package parking.back.repository;

import parking.back.modul.Car;

import java.util.List;
import java.util.UUID;

public interface CarController {
    boolean save(Car car);
    boolean remove(UUID uuid);
    List<Car> getAll();
    Car findById(UUID uuid);
}
