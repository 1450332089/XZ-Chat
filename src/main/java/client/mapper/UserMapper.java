package client.mapper;

import client.pojo.User;

import java.util.Map;

public interface UserMapper {
    String logIn(int id);
    int addUser(User user);
    int updateName(Map<String,Object> map);
    String getName(int id);
}
