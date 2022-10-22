package client.controller;

import client.frame.ChatFrame;

import java.awt.*;
import java.util.Map;

public interface ChatController {
    public void setChatFrame(ChatFrame chatFrame);
    public void initClient();
    public void logIn();
    public void logOut();
    public void sendMessage(Map messageMap);
    public void sendIndividualMessage(String username,int toId,String date,String msg);
    public void receiveMessage(String str);
    public void addText(Map messageMap , Color col, boolean bold, int fontSize);
    public void addText(String str , Color col, boolean bold, int fontSize);
}
