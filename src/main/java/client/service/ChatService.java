package client.service;

import client.frame.ChatFrame;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public interface ChatService {
    public void setChatFrame(ChatFrame chatFrame);
    public void initClient();
    public void logIn();
    public void logOut();
    public void sendMessage(Map messageMap);
    public void sendIndividualMessage(int toId,String msg);
    public String receiveMessage();
}
