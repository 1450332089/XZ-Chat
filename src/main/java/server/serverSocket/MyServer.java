package server.serverSocket;

import server.pojo.MessageData;
import server.service.ChatService;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class MyServer {
    InetAddress address = null; //服务器地址
    int port = 18888;       //服务器端口号
    DatagramSocket socket;  //服务器socket
    int threadNum = 3;
    /*
    记录一个bug：
    一开始我直接private ChatService chatService = new ChatServiceImpl();
    后面chatServiceImpl里的Myserver会报空指针，因为第一次创建Myserver时，
    会在构造方法里新建ChatServiceImpl，而ChatServiceImpl的构造方法里又会MyServer.getInstance();
    这时静态内部类里的instance对象还未初始化，会返回null
     */
    private ChatService chatService;
    //单例模式
    private static class SingletonClassInstance{
        private static final MyServer instance=new MyServer();
    }
    public static MyServer getInstance(){
        return MyServer.SingletonClassInstance.instance;
    }
    private MyServer(){

    }

    public void init(){
        try {
            socket = new DatagramSocket(port);
            chatService = new ChatServiceImpl();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        Runnable runnable = () -> {
            try {
                receiveMessage();		//调用接收函数
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        for (int i = 0; i < threadNum; i++) {
            executorService.execute(runnable);
        }
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
            MessageData messageData = new MessageData(clientIP, clientPort, str);
            chatService.processMessage(messageData);
        }

    }
}
