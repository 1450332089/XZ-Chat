package server.serverSocket;

import server.service.ChatServiceImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.pojo.EmojiIcon;
import common.pojo.LoggedUser;
import common.pojo.Message;
import common.utils.Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MyServer {
    InetAddress address = null; //服务器地址
    int port = 18888;       //服务器端口号
    DatagramSocket socket;  //服务器socket
    Map<Integer, LoggedUser> clientMap = new HashMap<>();    //存放用户信息的集合
    //单例模式
    private static class SingletonClassInstance{
        private static final MyServer instance=new MyServer();
    }
    public static MyServer getInstance(){
        return MyServer.SingletonClassInstance.instance;
    }

    public void init(){
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            try {
                receiveMessage();		//调用接收函数
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void sendMessage(String msg,String ip,int port){
        try {
            System.out.println("发送的json字符串为："+msg);
            InetAddress address = InetAddress.getByName(ip);
            byte[] buf = msg.getBytes("UTF-8");
            DatagramPacket dataGramPacket = new DatagramPacket(buf, buf.length, address, port);
            socket.send(dataGramPacket);  //通过套接字发送数据
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //接收消息函数
    public void receiveMessage() throws Exception{
        while(true){
            address = InetAddress.getLocalHost();
//            String ip = address.getHostAddress();

            byte[] byte01=new byte[1024];
            DatagramPacket packet =new DatagramPacket(byte01,byte01.length);//创建包接收信息
            socket.receive(packet );//接收信息

            InetAddress clientAddress = packet.getAddress();
            String clientIP = clientAddress.getHostAddress();//获得客户端的IP地址
            int clientPort = packet.getPort(); //获得客户端的端口号

            byte[] dataArray = packet.getData();
            int length = packet.getLength();
            String str= null;//将接收的信息转换为String型
            try {
                str = new String(dataArray,0,length,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //将JsonString转化为jsonObject，然后取值
            JSONObject jsonObject = Utils.String2Json(str);
            int id = (int) jsonObject.get("id");
            String name = (String) jsonObject.get("name");
            String text = (String) jsonObject.get("text");
            //将jsonArray转换为ArrayList
            JSONArray emojiJsonArray = (JSONArray) jsonObject.get("emoji");
            ArrayList<EmojiIcon> emojiIconList = null;
            if(emojiJsonArray!=null){
                emojiIconList = new ArrayList<>();
                for (int i = 0; i < emojiJsonArray.size(); i++) {
                    int id1 = (int) emojiJsonArray.getJSONObject(i).get("id");
                    int location = (int) emojiJsonArray.getJSONObject(i).get("location");
                    EmojiIcon emojiIcon = new EmojiIcon(location,id1);
                    emojiIconList.add(emojiIcon);
                }
            }

            String date = (String) jsonObject.get("date");
            int type = (int) jsonObject.get("type");

            System.out.println("客户端map："+clientMap);

            HashMap<String,Object> messageMap = new HashMap<>();
            messageMap.put("text",text);
            messageMap.put("emoji",emojiIconList);
            messageMap.put("id",id);
            messageMap.put("name",name);

            ChatServiceImpl chatService = new ChatServiceImpl();
            switch (type){
                //收到了登录消息，则添加该用户
                case Message.REQUEST_LOGIN:
                    chatService.addUser(clientMap,id,name,clientIP,clientPort);
                    break;
                //收到了下线消息，则删除该用户
                case Message.REQUEST_LOGOUT:
                    chatService.delUser(clientMap,id);
                    break;
                //收到群聊文字消息，则广播该消息
                case Message.MESSAGE_PUBLIC:
                    System.out.println("收到文字"+str);
                    chatService.broadcast(clientMap,messageMap);
                    break;
                //收到私聊文字消息，则只向对应用户转发该消息
                case Message.MESSAGE_PRIVATE:
                    System.out.println("收到私聊请求"+str);
                    int toId = (int) jsonObject.get("toId");
                    chatService.individualCast(clientMap,toId,str);
                    break;
                default:break;
            }
        }

    }
}
