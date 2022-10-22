package common.pojo;

import common.utils.Utils;

import java.util.ArrayList;


public class Message {
    public static final int REQUEST_LOGIN = 0;  //登录
    public static final int REQUEST_LOGOUT = -1;//登出
    public static final int MESSAGE_PUBLIC = 1;//公聊
    public static final int MESSAGE_PRIVATE = 2;//私聊
    public static final int REQUEST_ADD = 3;//添加在线列表
    public static final int REQUEST_DEL = 4;//减少在线列表
    public static final int REQUEST_REFRESH = 5;//更新在线列表


    private int type;
    private String name;
    private int id;
    private String text;
    private ArrayList<EmojiIcon> emoji;
    private ArrayList<User> users;
    private String date;

    public Message(int type,int id, String name, String msg, ArrayList<EmojiIcon> emojiIconList ) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.text = msg;
        this.emoji = emojiIconList;
        this.date = Utils.getDate();
    }

    public Message(int type,int id, String name, ArrayList<User> users) {
        this.type = type;
        this.name = name;
        this.id = id;
        this.users = users;
    }

    public Message(int type,int id, String name,  String text, ArrayList<EmojiIcon> emoji, ArrayList<User> users, String date) {
        this.type = type;
        this.name = name;
        this.id = id;
        this.text = text;
        this.emoji = emoji;
        this.users = users;
        this.date = date;
    }

    public Message(int type, int id, String name, String msg) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.text = msg;
        this.date = Utils.getDate();
    }

    public Message(int type,int id, String name) {
        this.type = type;
        this.name = name;
        this.id = id;
    }

    public Message(int type, ArrayList<User> users) {
        this.type = type;
        this.users = users;
    }

    public Message() {

    }


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<EmojiIcon> getEmoji() {
        return emoji;
    }

    public void setEmoji(ArrayList<EmojiIcon> emoji) {
        this.emoji = emoji;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", text='" + text + '\'' +
                ", emoji=" + emoji +
                ", users=" + users +
                ", date='" + date + '\'' +
                '}';
    }
}
