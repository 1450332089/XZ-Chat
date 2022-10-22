package client.pojo;

import common.pojo.LoggedUser;

public class User {
    private int id;
    private String name;
    private String password;


    public static LoggedUser loggedUser;

    public static LoggedUser getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(LoggedUser loggedUser) {
        User.loggedUser = loggedUser;
    }



    public User() {

    }

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
