package com.example.chatrmi.client;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class EmojiGroup extends JButton {
    private int icons;

    public int getIcons() {
        return this.icons;
    }

    public void setIcons(int icons){
        this.icons = icons;
    }

    public EmojiGroup(String icon, int icons){
        this.icons = icons;
        this.setContentAreaFilled(false);
        this.setBorder((Border)null);
        this.setPreferredSize(new Dimension(50, 32));
        this.setCursor(new Cursor(12));
        this.setIcon(new ImageIcon("emoji/" + icon));
    }
}
