package parking.back.repository;

import parking.back.modul.User;
import parking.utils.Colors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserControllerImpl implements UserController {
    private static UserController instance;
    private UserControllerImpl(){}

    public static UserController getInstance() {
        if (instance == null){
            synchronized (UserControllerImpl.class){
                if (instance == null){
                    instance = new UserControllerImpl();
                }
            }
        }
        return instance;
    }
    @Override
    public User findUserByUsername(String username) {
        try {
            List<User> users = read();
            if (users == null)  return null;
            for (User user : users) {
                if (user.getUsername().equals(username))    return user;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //throw new RuntimeException(Colors.RED + "There is no user with this username" + Colors.RESET);
        return null;
    }

    @Override
    public boolean addUser(User user) {
        User user1 = findUserByUsername(user.getUsername());
        if (user1 != null)  throw new RuntimeException(Colors.RED + "This user is exists" + Colors.RESET);
        List<User> users = read();
        if (users == null)  users = new ArrayList<>();
        //ArrayList<User> users = new ArrayList<>();
        users.add(user);

        write(users);
        return true;
    }


    private void write(List<User> users) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream("repository/users.txt");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                ){
            objectOutputStream.writeObject(users);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean removeUser(String username) {
        User user = findUserByUsername(username);
        if (user == null)   throw new RuntimeException(Colors.RED + "There is no user with this username" + Colors.RESET);;
        List<User> users = read();
        users.remove(user);
        write(users);
        return true;
    }

    @SuppressWarnings("unchecked")
    private List<User> read(){
        File file = new File("repository/users.txt");
        if (file.length() == 0)     return new ArrayList<>();
        try (
                FileInputStream fileInputStream = new FileInputStream("repository/users.txt");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ){
            List<User> users = (List<User>) objectInputStream.readObject();
            if (users != null)  return users;
        }catch (Exception e){
            e.printStackTrace();
            //System.out.println(e.getMessage());
        }
        return null;
    }
}
