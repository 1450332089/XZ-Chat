package client.frame;

import common.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 表情框
 */
public class PicsJWindow extends JWindow {
    private static final long serialVersionUID = 1L;
    GridLayout gridLayout1 = new GridLayout(7, 15);
    JLabel[] ico = new JLabel[105]; /*放表情*/
    int i;
    ChatFrame owner;

    public PicsJWindow(ChatFrame owner) {
        super(owner);
        this.owner = owner;
        try {
            init();
            this.setAlwaysOnTop(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void init() throws Exception {
        this.setPreferredSize(new Dimension(28 * 15, 28 * 7));
        JPanel p = new JPanel();
        p.setOpaque(true);
        this.setContentPane(p);
        p.setLayout(gridLayout1);
        p.setBackground(SystemColor.text);
        String fileName = "";
        for (i = 0; i < ico.length; i++) {
            ico[i] = new JLabel(Utils.getImageIcon(i), SwingConstants.CENTER);
            //设置编号
            ico[i].setBorder(BorderFactory.createLineBorder(new Color(225, 225, 225), 1));
            //设置备注
            ico[i].setToolTipText(i + "");
            ico[i].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == 1) {
                        JLabel cubl = (JLabel) (e.getSource());
                        ImageIcon icon = (ImageIcon) cubl.getIcon();
                        owner.setInputAreaEmoji(icon);
                        cubl.setBorder(BorderFactory.createLineBorder(new Color(225, 225, 225), 1));
                        getObj().dispose();
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    ((JLabel) e.getSource()).setBorder(BorderFactory.createLineBorder(Color.BLUE));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    ((JLabel) e.getSource()).setBorder(BorderFactory.createLineBorder(new Color(225, 225, 225), 1));
                }

            });
            //添加到pane里
            p.add(ico[i]);
        }
        p.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                getObj().dispose();
            }

        });
    }

    @Override
    public void setVisible(boolean show) {
        if (show) {
            determineAndSetLocation();
        }
        super.setVisible(show);
    }

    private void determineAndSetLocation() {
        Point loc = owner.getButton_emoji().getLocationOnScreen();/*控件相对于屏幕的位置*/
        setBounds(loc.x - getPreferredSize().width / 3, loc.y - getPreferredSize().height,
                getPreferredSize().width, getPreferredSize().height);
    }

    private JWindow getObj() {
        return this;
    }

} 