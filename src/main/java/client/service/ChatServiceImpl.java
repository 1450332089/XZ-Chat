package client.service;

import client.clientSocket.MyClient;
import client.frame.ChatFrame;
import client.pojo.User;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.pojo.EmojiIcon;
import common.pojo.LoggedUser;
import common.pojo.Message;
import common.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatServiceImpl implements ChatService{
    private MyClient myClient;
    private ChatFrame chatFrame;
    private LoggedUser user = User.getLoggedUser();
    public void initClient(){
        myClient = new MyClient();
        myClient.connect();
    }

    public void setChatFrame(ChatFrame chatFrame) {
        this.chatFrame = chatFrame;
    }

    @Override
    public void logIn() {
        Message message = new Message(Message.REQUEST_LOGIN, user.getId(),user.getName());
        String jsonString = Utils.getJsonString(message);
        myClient.sendMessage(jsonString);
    }

    @Override
    public void logOut() {
        Message message = new Message(Message.REQUEST_LOGOUT, user.getId(),user.getName());
        String jsonString = Utils.getJsonString(message);
        myClient.sendMessage(jsonString);
    }

    /*
    将map中的文本和表情取出，然后生成json字符串，传输json字符串
     */
    @Override
    public void sendMessage(Map messageMap) {
        String msg = (String) messageMap.get("text");
        ArrayList<EmojiIcon> emojiIconList = (ArrayList<EmojiIcon>) messageMap.get("emoji");
        Message message = new Message(Message.MESSAGE_PUBLIC, user.getId(),user.getName(), msg,emojiIconList);
        String jsonString = Utils.getJsonString(message);
        myClient.sendMessage(jsonString);
    }

    //给指定id的用户发送私聊消息
    @Override
    public void sendIndividualMessage(int toId, String msg) {
        Message message = new Message(Message.MESSAGE_PRIVATE, user.getId(),user.getName(), msg);
        JSONObject jsonObject = Utils.getJsonObject(message);
        jsonObject.put("toId",toId);
        String s = Utils.Json2String(jsonObject);
        myClient.sendMessage(s);
        System.out.println("[发送私聊]私聊发送给"+toId+",内容为："+s);
    }

    @Override
    public String receiveMessage() {
        return myClient.receiveMessage();
    }
}
