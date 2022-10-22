import client.service.ChatServiceImpl;
import client.service.UserService;
import client.service.UserServiceImpl;
import com.alibaba.fastjson.JSONObject;
import common.pojo.Message;
import common.utils.Utils;
import org.junit.Test;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class Mytest {

    @Test
    public void testLogin() {
        UserService userService = new UserServiceImpl();
        String res = userService.logIn(186);
        if(res.equals("123456")){
            System.out.println("登陆成功");
        }
        else {
            System.out.println("登陆失败");
        }
    }
    @Test
    public void testUser(){
        InetAddress ip = null;
        try {
             ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(ip.getHostAddress());

    }
    @Test
    public void testMsg(){
        ChatServiceImpl chatService = new ChatServiceImpl();
        chatService.sendIndividualMessage(145,"hahaha");

    }
    @Test
    public void testJson() {
        JSONObject object = new JSONObject();
        //string
        object.put("string","string");
        //int
        object.put("int",2);
        //boolean
        object.put("boolean",true);
        //array
        List<Integer> integers = Arrays.asList(1,2,3);
        object.put("list",integers);
        //null
        object.put("null",null);
        String s = Utils.Json2String(object);
        System.out.println(s);
    }
    @Test
    public void testEmoji() {
        TestFrame testFrame = new TestFrame();
        JTextPane textPane1 = testFrame.getTextPane1();
        textPane1.insertIcon(new ImageIcon("emoji/1.gif"));
        testFrame.setVisible(true);
    }
}
