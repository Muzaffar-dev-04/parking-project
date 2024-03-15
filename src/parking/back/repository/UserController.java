package parking.back.repository;

import parking.back.modul.User;

public interface UserController {
    User findUserByUsername(String username);
    boolean addUser(User user);
    boolean removeUser(String username);
}
