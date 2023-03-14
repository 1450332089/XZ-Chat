package server.service;


import common.pojo.LoggedUser;
import server.pojo.MessageData;
import server.serverSocket.MyServer;

import java.util.Map;

public interface ChatService {
    void processMessage(MessageData msg);
    void addUser(int id, String name, String ip, int port);
    void delUser(int id);
    void broadcast(Map<String , Object> messageMap);
    void individualCast(int toId,String msg);
}
