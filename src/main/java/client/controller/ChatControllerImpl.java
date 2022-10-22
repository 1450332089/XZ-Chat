package client.controller;

import client.frame.ChatFrame;
import client.pojo.User;
import client.service.ChatService;
import client.service.ChatServiceImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.pojo.EmojiIcon;
import common.pojo.LoggedUser;
import common.pojo.Message;
import common.utils.Utils;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatControllerImpl implements ChatController {
    private ChatFrame chatFrame;
    private ChatService chatService = new ChatServiceImpl();

    public ChatControllerImpl() {

    }

    @Override
    public void setChatFrame(ChatFrame chatFrame) {
        this.chatFrame = chatFrame;
    }

    @Override
    public void initClient() {
        chatService.initClient();

        new Thread(() -> {
            System.out.println("开启接收线程");
            while (true){
                String str = chatService.receiveMessage();
                receiveMessage(str);
            }
        }).start();


    }

    @Override
    public void logIn() {
        chatService.logIn();
    }

    @Override
    public void logOut() {
        chatService.logOut();
        chatFrame.dispose();
    }

    @Override
    public void sendMessage(Map messageMap) {
        //显示发送的群聊消息
        addText("我："+ Utils.getDate(),Color.red,false,18);
        addText(messageMap,Color.black,true,20);
        chatFrame.getTextPane_Input().setText("");
        chatService.sendMessage(messageMap);
    }

    @Override
    public void sendIndividualMessage(String username, int toId, String date, String msg) {
        //显示发送的私聊消息
        addText("我对"+username+"私聊："+date,Color.pink,true,18);
        addText(msg,Color.black,true,20);

        chatService.sendIndividualMessage(toId,msg);
    }

    //根据接受到的消息，更新视图
    @Override
    public void receiveMessage(String str) {
        //将JsonString转化为jsonObject，然后取值
        JSONObject jsonObject = Utils.String2Json(str);
        String name = (String) jsonObject.get("name");

        String text = (String) jsonObject.get("text");

        String date = (String) jsonObject.get("date");
        int id = (int) jsonObject.get("id");
        int type = (int) jsonObject.get("type");

        chatFrame = ChatFrame.getInstance();
        LoggedUser user = User.getLoggedUser();
        switch (type){
            case Message.MESSAGE_PUBLIC:
                System.out.println("[收到群聊]"+str);
                if(id!=user.getId()) {  //当消息是其他人发来的
                    //将jsonArray转换为ArrayList
                    JSONArray emojiJsonArray = (JSONArray) jsonObject.get("emoji");
                    ArrayList<EmojiIcon> emojiIconList = null;
                    if(emojiJsonArray!=null){
                        emojiIconList = new ArrayList<>();
                        for (int i = 0; i < emojiJsonArray.size(); i++) {
                            int iconId = (int) emojiJsonArray.getJSONObject(i).get("id");
                            int location = (int) emojiJsonArray.getJSONObject(i).get("location");
                            EmojiIcon emojiIcon = new EmojiIcon(location,iconId);
                            emojiIconList.add(emojiIcon);
                        }
                    }
                    HashMap<String,Object> messageMap = new HashMap<>();
                    messageMap.put("text",text);
                    messageMap.put("emoji",emojiIconList);
                    //显示接受的群聊消息
                    addText(name+"："+date,Color.blue,false,18);
                    addText(messageMap,Color.black,true,20);
                }
                break;
            case Message.MESSAGE_PRIVATE:
                System.out.println("[收到私聊]"+str);
                //显示接受的私聊消息
                addText(name+"对我私聊："+date,Color.pink,true,18);
                addText(text,Color.black,true,20);
                break;
            case Message.REQUEST_ADD:
                System.out.println("[添加用户]"+str);
                chatFrame.addUser(String.format("%s(%d)",name,id));
                break;
            case Message.REQUEST_DEL:
                System.out.println("[删除用户]"+str);
                chatFrame.delUser(String.format("%s(%d)",name,id));
                break;
            case Message.REQUEST_REFRESH:
                System.out.println("[加载用户]"+str);
                JSONArray userJsonArray = (JSONArray) jsonObject.get("users");
                ArrayList<String> userList = new ArrayList<>();
                for (int i = 0; i < userJsonArray.size(); i++) {
                    int userId = (int) userJsonArray.getJSONObject(i).get("id");
                    String userName = (String) userJsonArray.getJSONObject(i).get("name");
                    userList.add(String.format("%s(%d)",userName,userId));
                }
                String[] userArray = userList.toArray(new String[0]);
                chatFrame.loadUser(userArray);
                break;
            default:break;
        }
    }
    @Override
    //在聊天文字栏显示文字加表情
    public void addText(Map messageMap , Color col, boolean bold, int fontSize) {
        JTextPane textPane_Chat = chatFrame.getTextPane_Chat();
        StyledDocument chatDoc;
        String str = (String) messageMap.get("text");
        ArrayList<EmojiIcon> emojiIconList = (ArrayList<EmojiIcon>) messageMap.get("emoji");
        SimpleAttributeSet attrSet = new SimpleAttributeSet();
        StyleConstants.setForeground(attrSet, col);//设置颜色
        if (bold) {
            StyleConstants.setBold(attrSet, bold);//设置粗体
        }
        StyleConstants.setFontSize(attrSet, fontSize);//设置字号

        chatDoc = textPane_Chat.getStyledDocument();
        str = "\n" + str;
        try {
            int position = chatDoc.getLength();
            chatDoc.insertString(position, str, attrSet);

            if(emojiIconList!=null) {
                for (EmojiIcon emojiIcon : emojiIconList) {
                    int id = emojiIcon.getId();
                    ImageIcon imageIcon = Utils.getImageIcon(id);
                    textPane_Chat.setCaretPosition(position+emojiIcon.getLocation()+1);
                    textPane_Chat.insertIcon(imageIcon);
                }
            }
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, "字体设置错误！", "提示", JOptionPane.ERROR_MESSAGE);
        }
    }
    @Override
    //重载，只显示文字
    public void addText(String str , Color col, boolean bold, int fontSize) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("text",str);
        addText(map,col,bold,fontSize);
    }
}
