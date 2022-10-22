package server.service;


import common.pojo.LoggedUser;
import server.serverSocket.MyServer;

import java.util.Map;

public interface ChatService {
    public void addUser(Map<Integer, LoggedUser> map, int id, String name, String ip, int port);
    public void delUser(Map<Integer, LoggedUser> map, int id);
    public void broadcast(Map<Integer, LoggedUser> userMap, Map<String , Object> messageMap);
    public void individualCast(Map<Integer, LoggedUser> userMap,int toId,String msg);
}
