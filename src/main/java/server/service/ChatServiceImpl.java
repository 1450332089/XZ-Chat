package server.service;

import common.pojo.EmojiIcon;
import common.pojo.LoggedUser;
import common.pojo.Message;
import common.pojo.User;
import common.utils.Utils;
import server.serverSocket.MyServer;

import java.util.*;

public class ChatServiceImpl implements ChatService{
    private MyServer myServer = MyServer.getInstance();

    //增加了新用户，则通知客户机更新用户列表
    @Override
    public void addUser(Map<Integer, LoggedUser> map, int id, String name, String ip, int port) {
        LoggedUser loggedUser = new LoggedUser(id, name, ip, port);


        Message message = new Message(Message.REQUEST_ADD, id, name);
        String jsonString = Utils.getJsonString(message);
        ArrayList<User> users = new ArrayList<>();
        //向所有客户机发送增加用户指令
        Iterator<Map.Entry<Integer, LoggedUser>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, LoggedUser> entry = entries.next();
            LoggedUser user = entry.getValue();
            users.add(new User(user.getId(),user.getName()));
            //向对应的地址发送数据
            myServer.sendMessage(jsonString,user.getIp(),user.getPort());
        }

        //将该客户端地址放入map中
        map.put(id,loggedUser);
        System.out.println("用户"+id+"已存入map中");
        users.add(new User(id,name));
        //让新用户加载已存在的用户列表
        Message message1 = new Message(Message.REQUEST_REFRESH,id,name,users);

        String jsonString1 = Utils.getJsonString(message1);
        System.out.println("发送的用户列表json："+jsonString1);
        myServer.sendMessage(jsonString1,ip,port);

    }
    //下线一位用户，通知所有客户机删除该用户列表
    @Override
    public void delUser(Map<Integer, LoggedUser> map, int id) {
        LoggedUser loggedUser = map.get(id);
        map.remove(id);
        System.out.println("已移除用户"+id);

        Message message = new Message(Message.REQUEST_DEL, loggedUser.getId(),loggedUser.getName());
        String jsonString = Utils.getJsonString(message);
        //向所有客户机发送删除用户指令
        Iterator<Map.Entry<Integer, LoggedUser>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, LoggedUser> entry = entries.next();
            LoggedUser user = entry.getValue();
            myServer.sendMessage(jsonString,user.getIp(),user.getPort());
        }
    }

    //向所有客户机广播消息
    @Override
    public void broadcast(Map<Integer, LoggedUser> userMap, Map<String, Object> messageMap) {
        String msg = (String) messageMap.get("text");
        ArrayList<EmojiIcon> emojiIconList = (ArrayList<EmojiIcon>) messageMap.get("emoji");
        int id = (int) messageMap.get("id");
        String name = (String) messageMap.get("name");
        //遍历用户map
        Iterator<Map.Entry<Integer, LoggedUser>> entries = userMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, LoggedUser> entry = entries.next();
            LoggedUser user = entry.getValue();
            Message message = new Message(Message.MESSAGE_PUBLIC, id, name, msg,emojiIconList);
            String jsonString = Utils.getJsonString(message);
            //向对应的地址发送数据
            myServer.sendMessage(jsonString,user.getIp(),user.getPort());
        }
    }
    //向指定客户机发消息
    @Override
    public void individualCast(Map<Integer, LoggedUser> userMap, int toId, String msg) {
        Iterator<Map.Entry<Integer, LoggedUser>> entries = userMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, LoggedUser> entry = entries.next();
            LoggedUser user = entry.getValue();
            if(user.getId()==toId){
                myServer.sendMessage(msg,user.getIp(),user.getPort());
                break;
            }
        }
    }


}
