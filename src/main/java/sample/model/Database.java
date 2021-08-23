package sample.model;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

public class Database {
    private static Database database;

    private Database() {
    }

    public static Database getInstance() {
        if (database == null)
            database = new Database();
        return database;
    }

    private final String USERS_PATH = "./database/users/";

    public HashMap<String, User> updateAllUsers() {
        Gson gson = new Gson();
        HashMap<String, User> allUsers = new HashMap<>();

        File[] listOfFiles = new File(USERS_PATH).listFiles();
        assert listOfFiles != null;

        Arrays.stream(listOfFiles)   // Don't use parallel without proper testing. (may cause crash)
                .filter(this::isJsonFile)
                .forEach(file -> {
                    try {
                        BufferedReader reader = new BufferedReader(
                                new FileReader(USERS_PATH + file.getName()));
                        User user = gson.fromJson(reader, User.class);
                        allUsers.put(user.getUsername(), user);
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (FileNotFoundException ignored) {
                    }
                });

        return allUsers;
    }

    private boolean isJsonFile(File file) {
        String fileName = file.getName();
        int i = fileName.lastIndexOf('.');
        return i > 0 && fileName.substring(i + 1).equals("json");
    }

    public void saveUser(User user) {
        try {
            File file = new File(USERS_PATH + user.getUsername() + ".json");
            Writer writer = new FileWriter(file);
            new Gson().toJson(user, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeUser(User user) {
        String path = USERS_PATH + user.getUsername() + ".json";
        try {
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
