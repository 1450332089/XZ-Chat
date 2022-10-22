import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;
/*
 * Created by JFormDesigner on Mon Dec 06 14:26:50 CST 2021
 */



/**
 * @author 新政 王
 */
public class TestFrame extends JFrame {
    public TestFrame() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        scrollPane1 = new JScrollPane();
        textPane1 = new JTextPane();

        //======== this ========
        Container contentPane = getContentPane();

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(textPane1);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(37, 37, 37)
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(153, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(58, 58, 58)
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(97, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane scrollPane1;
    private JTextPane textPane1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public JScrollPane getScrollPane1() {
        return scrollPane1;
    }

    public void setScrollPane1(JScrollPane scrollPane1) {
        this.scrollPane1 = scrollPane1;
    }

    public JTextPane getTextPane1() {
        return textPane1;
    }

    public void setTextPane1(JTextPane textPane1) {
        this.textPane1 = textPane1;
    }
}
