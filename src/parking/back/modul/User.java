package parking.back.modul;

import java.io.Serializable;

public class User implements Serializable {
    private Car car;
    private String username;
    private String password;

    public User(Car car, String username, String password) {
        this.car = car;
        this.username = username;
        this.password = password;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
