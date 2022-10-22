package server;

import server.serverSocket.MyServer;

public class ServerMain {
    public static void main(String[] args) {
        System.out.println("开启接收线程");
        MyServer server = MyServer.getInstance();
        server.init();

    }
}
