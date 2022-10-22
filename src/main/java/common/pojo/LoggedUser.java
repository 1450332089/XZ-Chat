package common.pojo;

public class LoggedUser {
    private int id;
    private String name;
    private String ip;
    private int port;



    public LoggedUser(int id, String name, String ip, int port) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public LoggedUser(int id, String name, String ip) {
        this.id = id;
        this.name = name;
        this.ip = ip;
    }

    public LoggedUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "LoggedUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
