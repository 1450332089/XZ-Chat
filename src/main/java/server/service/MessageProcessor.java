package server.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.pojo.EmojiIcon;
import common.pojo.Message;
import common.utils.Utils;
import server.pojo.MessageData;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageProcessor {
    private ChatService chatService;
    public MessageProcessor(ChatService chatService) {
        this.chatService = chatService;
    }
    public void processMessage(MessageData msgData){
        String msg = msgData.getMsg();
        String clientIP = msgData.getIp();
        int clientPort = msgData.getPort();
        //将JsonString转化为jsonObject，然后取值
        JSONObject jsonObject = Utils.String2Json(msg);
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

        HashMap<String,Object> messageMap = new HashMap<>();
        messageMap.put("text",text);
        messageMap.put("emoji",emojiIconList);
        messageMap.put("id",id);
        messageMap.put("name",name);

        /*
        记录一个bug：这里chatService应该是成员变量，这样才能实现共享userHashMap
        改代码时，这里的语句为新建一个chatService临时变量。这样每个chatService都是新的，map也是新的
        出现了用户列表为空的bug。
         */
        switch (type){
            //收到了登录消息，则添加该用户
            case Message.REQUEST_LOGIN:
                chatService.addUser(id,name,clientIP,clientPort);
                break;
            //收到了下线消息，则删除该用户
            case Message.REQUEST_LOGOUT:
                chatService.delUser(id);
                break;
            //收到群聊文字消息，则广播该消息
            case Message.MESSAGE_PUBLIC:
                System.out.println(Thread.currentThread()+"收到文字"+msg);
                chatService.broadcast(messageMap);
                break;
            //收到私聊文字消息，则只向对应用户转发该消息
            case Message.MESSAGE_PRIVATE:
                System.out.println(Thread.currentThread()+"收到私聊请求"+msg);
                int toId = (int) jsonObject.get("toId");
                chatService.individualCast(toId,msg);
                break;
            default:break;
        }
    }
}
