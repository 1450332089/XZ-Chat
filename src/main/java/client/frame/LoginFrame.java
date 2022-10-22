/*
 * Created by JFormDesigner on Sun Dec 05 23:18:35 CST 2021
 */

package client.frame;

import java.awt.event.*;

import client.service.UserService;
import client.service.UserServiceImpl;

import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author 新政 王
 */
public class LoginFrame extends JFrame {
    private UserService userService;
    public LoginFrame() {
        initComponents();
    }
    //登录
    private void button1MouseClicked(MouseEvent e) {
        System.out.println("点击登录");
        //验证密码
        userService = new UserServiceImpl();
        int id = Integer.parseInt(id_TextFiled.getText());
        String password = String.valueOf(pwd_TextFiled.getPassword());
        String qpwd = userService.logIn(id);
        if(qpwd!=null) {
            if (qpwd.equals(password)) {
                JOptionPane.showMessageDialog(this, "登陆成功！");
                String name = userService.getName(id);
                //注入用户对象
                userService.setUser(id, name);
                //跳转到主页面
                this.dispose();
                ChatFrame chatFrame = ChatFrame.getInstance();
                chatFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "用户名密码错误", "警告", JOptionPane.WARNING_MESSAGE);
            }

            userService.close();
        }else {
            JOptionPane.showMessageDialog(this, "账号不存在", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }
    //跳转到注册界面
    private void label4MouseClicked(MouseEvent e) {
        System.out.println("点击注册");
        RegisterFrame registerFrame = new RegisterFrame();
        registerFrame.setVisible(true);
    }

    private void button1KeyPressed(KeyEvent e) {
        // TODO add your code here
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        id_TextFiled = new JTextField();
        label2 = new JLabel();
        label3 = new JLabel();
        pwd_TextFiled = new JPasswordField();
        button1 = new JButton();
        label4 = new JLabel();

        //======== this ========
        Container contentPane = getContentPane();

        //---- label1 ----
        label1.setText("\u8d26\u53f7");
        label1.setFont(new Font("\u6977\u4f53", Font.PLAIN, 20));

        //---- label2 ----
        label2.setText("\u767b\u5f55\u804a\u5929\u5ba4");
        label2.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 28));
        label2.setForeground(Color.red);

        //---- label3 ----
        label3.setText("\u5bc6\u7801");
        label3.setFont(new Font("\u6977\u4f53", Font.PLAIN, 20));

        //---- button1 ----
        button1.setText("\u767b\u5f55");
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button1MouseClicked(e);
            }
        });
        button1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                button1KeyPressed(e);
            }
        });

        //---- label4 ----
        label4.setText("\u6ce8\u518c\u8d26\u53f7");
        label4.setForeground(new Color(0, 179, 239));
        label4.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
        label4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                label4MouseClicked(e);
            }
        });

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(87, 87, 87)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(label2, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
                                .addComponent(id_TextFiled, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label3, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(label4))
                                .addComponent(pwd_TextFiled, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(109, 109, 109)
                            .addComponent(button1)))
                    .addContainerGap(107, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(46, 46, 46)
                    .addComponent(label2, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(id_TextFiled, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(label3, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(pwd_TextFiled, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(label4)
                    .addGap(18, 18, 18)
                    .addComponent(button1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(45, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JTextField id_TextFiled;
    private JLabel label2;
    private JLabel label3;
    private JPasswordField pwd_TextFiled;
    private JButton button1;
    private JLabel label4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
