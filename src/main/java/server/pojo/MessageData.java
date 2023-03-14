package server.pojo;

import java.util.Map;
public class MessageData {
    private String ip;
    private int port;
    private String msg;

    public MessageData(String ip, int port, String msg) {
        this.ip = ip;
        this.port = port;
        this.msg = msg;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getMsg() {
        return msg;
    }

}
