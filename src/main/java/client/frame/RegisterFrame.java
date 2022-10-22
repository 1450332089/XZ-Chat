/*
 * Created by JFormDesigner on Sun Dec 05 22:28:02 CST 2021
 */

package client.frame;

import client.pojo.User;
import client.service.UserService;
import client.service.UserServiceImpl;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author 新政 王
 */
public class RegisterFrame extends JFrame {
    private UserService userService;
    public RegisterFrame() {
        initComponents();
    }

    private void button1MouseClicked(MouseEvent e) {
        userService = new UserServiceImpl();
        int id = Integer.parseInt(id_TextFiled.getText());
        String name = name_TextFiled.getText();
        String password = String.valueOf(pwd_TextFiled.getPassword());
        String s = userService.logIn(id);
        if(name!=null && password!=null && new Integer(id) != null) {
            if (s != null) {
                JOptionPane.showMessageDialog(this, "账号已存在", "警告", JOptionPane.WARNING_MESSAGE);
            } else {
                int res = userService.addUser(new User(id, name, password));
                if (res == 1) {
                    JOptionPane.showMessageDialog(this, "注册成功");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "注册失败", "警告", JOptionPane.WARNING_MESSAGE);
                }
            }
        userService.close();
        }else{
            JOptionPane.showMessageDialog(this, "禁止输入为空", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        id_TextFiled = new JTextField();
        pwd_TextFiled = new JPasswordField();
        name_TextFiled = new JTextField();
        button1 = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("\u8d26\u53f7");
        label1.setFont(new Font("\u6977\u4f53", Font.PLAIN, 20));
        contentPane.add(label1);
        label1.setBounds(82, 64, 51, 43);

        //---- label2 ----
        label2.setText("\u5bc6\u7801");
        label2.setFont(new Font("\u6977\u4f53", Font.PLAIN, 20));
        contentPane.add(label2);
        label2.setBounds(82, 120, 51, 43);

        //---- label3 ----
        label3.setText("\u6635\u79f0");
        label3.setFont(new Font("\u6977\u4f53", Font.PLAIN, 20));
        contentPane.add(label3);
        label3.setBounds(82, 176, 51, 43);
        contentPane.add(id_TextFiled);
        id_TextFiled.setBounds(151, 65, 194, 43);
        contentPane.add(pwd_TextFiled);
        pwd_TextFiled.setBounds(151, 121, 194, 43);
        contentPane.add(name_TextFiled);
        name_TextFiled.setBounds(151, 177, 194, 43);

        //---- button1 ----
        button1.setText("\u786e\u8ba4");
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button1MouseClicked(e);
            }
        });
        contentPane.add(button1);
        button1.setBounds(192, 238, 105, 37);

        contentPane.setPreferredSize(new Dimension(465, 345));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTextField id_TextFiled;
    private JPasswordField pwd_TextFiled;
    private JTextField name_TextFiled;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
