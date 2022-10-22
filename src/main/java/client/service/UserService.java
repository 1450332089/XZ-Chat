package client.service;

import client.pojo.User;
import common.pojo.LoggedUser;

public interface UserService {
    public String logIn(int id);
    public int addUser(User user);
    public int updateName(int id,String name);
    public String getName(int id);
    public void setUser(int id,String name);

    public void close();
}
