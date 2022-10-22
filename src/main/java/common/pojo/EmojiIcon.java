package common.pojo;

import javax.swing.*;
import java.net.URL;

public class EmojiIcon {

    private int location;
    private int id;

    public EmojiIcon(int location, int id) {
        this.location = location;
        this.id = id;
    }

    public EmojiIcon() {

    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EmojiIcon{" +
                "location=" + location +
                ", id=" + id +
                '}';
    }

}
