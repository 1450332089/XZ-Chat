/*
 * Created by JFormDesigner on Sun Dec 05 20:03:25 CST 2021
 */

package client.frame;


import client.controller.ChatControllerImpl;
import client.pojo.User;
import common.pojo.EmojiIcon;
import common.pojo.LoggedUser;
import client.service.ChatService;
import client.service.ChatServiceImpl;
import common.utils.Utils;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.text.*;


/**
 * @author 新政 王
 */
public class ChatFrame extends JFrame implements MouseListener {
    private boolean isVisible = false;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        picsJWindow.setVisible(false);
        isVisible = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    //单例模式
      private static class SingletonClassInstance{
          private static final ChatFrame instance=new ChatFrame();
      }
      public static ChatFrame getInstance(){
          return SingletonClassInstance.instance;
      }

    private ChatControllerImpl chatController = new ChatControllerImpl();
    LoggedUser user = User.getLoggedUser();
    private StyledDocument inputDoc = null;
    private Map<String,Object> messageMap;
    private DefaultListModel usersModel = new DefaultListModel();
    public ChatFrame() {
        chatController.setChatFrame(this);
        //创建udp连接
        chatController.initClient();
        chatController.logIn();
        picsJWindow = new PicsJWindow(this);
        initComponents();
        label3.setText(user.getName());
        LoggedUser loggedUser = User.getLoggedUser();
        System.out.println("[当前账号]"+loggedUser);
    }


    //增加在线用户
    public void addUser(String name){
        usersModel.addElement(name);
        list1.setModel(usersModel);
    }
    //删除在线用户
    public void delUser(String name){
        usersModel.removeElement(name);
        list1.setModel(usersModel);
    }
    //加载在线用户
    public void loadUser(String[] userArray){
        for (int i = 0; i < userArray.length; i++) {
            usersModel.add(i,userArray[i]);
        }
        list1.setModel(usersModel);
    }
    //点击发送按钮
    private void button1MouseClicked(MouseEvent e) {
        String msg = getInputText();
        if(msg==null){
            JOptionPane.showMessageDialog(this,"禁止发送空消息","警告",JOptionPane.WARNING_MESSAGE);
        }
        else{
            List<EmojiIcon> emojiIconList = getInputEmojis();
            messageMap = new HashMap<>();
            messageMap.put("text",msg);
            messageMap.put("emoji",emojiIconList);

            chatController.sendMessage(messageMap);
        }
    }

    //点击表情按钮
    private void button_emojiMouseClicked(MouseEvent e) {
        picsJWindow.setVisible(!isVisible);
        isVisible = !isVisible;
    }
    //点击下线按钮
    private void button2MouseClicked(MouseEvent e) {
        chatController.logOut();
    }
    //点击私聊按钮
    private void button3MouseClicked(MouseEvent e) {
        String  info = (String) list1.getSelectedValue();
        String inputValue = null;
        if(info==null){
            JOptionPane.showMessageDialog(this,"请选择用户！","警告",JOptionPane.WARNING_MESSAGE);
        }

        String id = info.substring(info.lastIndexOf("(")+1,info.lastIndexOf(")"));
        String username = info.substring(0,info.indexOf("("));
        if(Integer.parseInt(id)==user.getId()){
            JOptionPane.showMessageDialog(this,"请不要选择自己！","警告",JOptionPane.WARNING_MESSAGE);
        }
        else {
            inputValue = JOptionPane.showInputDialog("请输入要发送给"+info+"的信息");
            chatController.sendIndividualMessage(username,Integer.parseInt(id),Utils.getDate(),inputValue);
        }

    }


    public void setChatArea(String str){
        textPane_Chat.setText(str);
    }
    public void setInputArea(String str){
        textPane_Input.setText(str);
    }
    public JTextPane getTextPane_Input() {
        return textPane_Input;
    }
    public JTextPane getTextPane_Chat() {
        return textPane_Chat;
    }



    public JButton getButton_emoji() {
        return button_emoji;
    }

    //在输入框插入表情
    public void setInputAreaEmoji(ImageIcon imgIc) {
        inputDoc = textPane_Input.getStyledDocument();
        textPane_Input.setCaretPosition(inputDoc.getLength());
        textPane_Input.insertIcon(imgIc); // 插入图片
    }
    //获取输入的表情
    public List<EmojiIcon> getInputEmojis(){
        ArrayList<EmojiIcon> emojiIconList = new ArrayList<>();
        inputDoc = textPane_Input.getStyledDocument();
        for (int i = 0; i < inputDoc.getLength(); i++) {
            if (inputDoc.getCharacterElement(i).getName().equals("icon")){
                Element ele = inputDoc.getCharacterElement(i);
                ImageIcon icon = (ImageIcon) StyleConstants.getIcon(ele.getAttributes());
                String description = icon.getDescription();
                String id = description.substring(description.lastIndexOf("/")+1,description.lastIndexOf(".gif"));
                EmojiIcon emojiIcon = new EmojiIcon(i, Integer.parseInt(id));
                emojiIconList.add(emojiIcon);
            }
        }
        return emojiIconList;
    }

    //获取输入的文字
    public String getInputText(){
        StringBuilder stringBuilder = new StringBuilder();
        inputDoc = textPane_Input.getStyledDocument();
        String text = null;
        for (int i = 0; i < inputDoc.getLength(); i++) {
            if (!inputDoc.getCharacterElement(i).getName().equals("icon")){
                try {
                    text = inputDoc.getText(i,1);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
                stringBuilder.append(text);
            }
        }
        return stringBuilder.toString();
    }

    private void _clear(ActionEvent e) {
        setChatArea("");
    }




    private void initComponents() {

        this.addMouseListener(this);
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        scrollPane2 = new JScrollPane();
        textPane_Input = new JTextPane();
        scrollPane3 = new JScrollPane();
        textPane_Chat = new JTextPane();
        button1 = new JButton();
        panel1 = new JPanel();
        button_emoji = new JButton();
        button_clear = new JButton();
        label2 = new JLabel();
        label3 = new JLabel();
        button2 = new JButton();
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        label4 = new JLabel();
        button3 = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(Color.white);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("\u7f51\u7edc\u804a\u5929\u5ba4");
        label1.setFont(new Font("\u96b6\u4e66", Font.BOLD, 28));
        contentPane.add(label1);
        label1.setBounds(22, 6, 213, 42);

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(textPane_Input);
        }
        contentPane.add(scrollPane2);
        scrollPane2.setBounds(25, 420, 465, 55);

        //======== scrollPane3 ========
        {

            //---- textPane_Chat ----
            textPane_Chat.setEditable(false);
            textPane_Chat.setFont(new Font("\u5b8b\u4f53", Font.PLAIN, 14));
            scrollPane3.setViewportView(textPane_Chat);
        }
        contentPane.add(scrollPane3);
        scrollPane3.setBounds(15, 50, 540, 325);

        //---- button1 ----
        button1.setText("\u53d1\u9001");
        button1.setBackground(new Color(0, 153, 153));
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button1MouseClicked(e);
            }
        });
        contentPane.add(button1);
        button1.setBounds(495, 420, 65, 55);

        //======== panel1 ========
        {

            //---- button_emoji ----
            button_emoji.setText("\u8868\u60c5");
            button_emoji.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    button_emojiMouseClicked(e);
                }
            });

            //---- button_clear ----
            button_clear.setText("\u6e05\u5c4f");
            button_clear.addActionListener(e -> _clear(e));

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(button_emoji, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_clear)
                        .addGap(0, 158, Short.MAX_VALUE))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(button_emoji, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(button_clear, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                        .addContainerGap())
            );
        }
        contentPane.add(panel1);
        panel1.setBounds(25, 380, 190, 35);

        //---- label2 ----
        label2.setFont(new Font("\u9ed1\u4f53", Font.BOLD, 18));
        label2.setText("\u6211\u7684\u6635\u79f0\uff1a");
        contentPane.add(label2);
        label2.setBounds(270, 5, 100, 42);

        //---- label3 ----
        label3.setFont(new Font("\u9ed1\u4f53", Font.BOLD, 18));
        contentPane.add(label3);
        label3.setBounds(365, 5, 100, 42);

        //---- button2 ----
        button2.setText("\u4e0b\u7ebf");
        button2.setForeground(Color.red);
        button2.setIcon(null);
        button2.setDefaultCapable(false);
        button2.setFocusable(false);
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button2MouseClicked(e);
            }
        });
        contentPane.add(button2);
        button2.setBounds(555, 5, 95, 35);

        //======== scrollPane1 ========
        {

            //---- list1 ----
            list1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            scrollPane1.setViewportView(list1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(565, 80, 95, 245);

        //---- label4 ----
        label4.setFont(new Font("\u9ed1\u4f53", Font.BOLD, 18));
        label4.setText("\u5728\u7ebf\u7528\u6237");
        contentPane.add(label4);
        label4.setBounds(560, 45, 100, 42);

        //---- button3 ----
        button3.setText("\u79c1\u804a");
        button3.setBackground(Color.pink);
        button3.setDefaultCapable(false);
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button3MouseClicked(e);
            }
        });
        contentPane.add(button3);
        button3.setBounds(565, 335, 85, 50);

        contentPane.setPreferredSize(new Dimension(685, 545));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }



    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JScrollPane scrollPane2;
    private JTextPane textPane_Input;
    private JScrollPane scrollPane3;
    private JTextPane textPane_Chat;
    private JButton button1;
    private JPanel panel1;
    private JButton button_emoji;
    private JButton button_clear;
    private JLabel label2;
    private JLabel label3;
    private JButton button2;
    private JScrollPane scrollPane1;
    private JList list1;
    private JLabel label4;
    private JButton button3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    private PicsJWindow picsJWindow;
}
