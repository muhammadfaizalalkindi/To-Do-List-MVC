package datauserstorage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import datauser.DataUser;

public class DataUserStorage {
    private static final String FILE_PATH = "userdata.txt";

    private DataUserStorage() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean addUser(DataUser user) {
        List<DataUser> users = getUsersFromFile();

        for (DataUser existingUser : users) {
            if (existingUser.getUsername().equals(user.getUsername())) {
                return false;
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            writer.println(user.getUsername() + "," + user.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean isValidUser(DataUser user) {
        List<DataUser> users = getUsersFromFile();

        for (DataUser existingUser : users) {
            if (existingUser.getUsername().equals(user.getUsername()) && existingUser.getPassword().equals(user.getPassword())) {
                return true;
            }
        }

        return false;
    }

    private static List<DataUser> getUsersFromFile() {
        List<DataUser> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 2) {
                    String username = userData[0];
                    String password = userData[1];
                    users.add(new DataUser(username, password));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }
}
