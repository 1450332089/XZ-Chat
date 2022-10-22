package client.clientSocket;

import client.frame.ChatFrame;
import client.pojo.User;
import client.service.UserServiceImpl;
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

public class MyClient {

    private String ip = "192.168.170.1";    //服务器ip
    private static int port = 18888;      //服务器的端口号
    private InetAddress address;
    DatagramSocket socket;
    //创建连接
    public void connect() {
        try {
            address = InetAddress.getByName(ip);
            socket = new DatagramSocket();  //创建套接字
        } catch (UnknownHostException|SocketException e) {
            e.printStackTrace();
        }

    }
    //发送消息函数
    public void sendMessage(String msg){
        try {
            System.out.println("[发送消息]发送的json字符串为："+msg);
            byte[] buf = msg.getBytes("UTF-8");
            DatagramPacket dataGramPacket = new DatagramPacket(buf, buf.length, address, port);
            socket.send(dataGramPacket);  //通过套接字发送数据
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //接收消息函数
    public String receiveMessage() {
            byte[] byte01=new byte[1024];
            DatagramPacket packet =new DatagramPacket(byte01,byte01.length,address,port);//创建包接收信息
            try {
                socket.receive(packet );//接收信息
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] dataArray = packet.getData();
            int length = packet.getLength();
            String str= null;//将接收的信息转换为String型
            try {
                str = new String(dataArray,0,length,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return str;
        }

}
