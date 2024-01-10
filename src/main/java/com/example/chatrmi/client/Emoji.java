package com.example.chatrmi.client;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Emoji extends JButton {
    public Emoji(String icon) {
        this.setContentAreaFilled(false);
        this.setBorder((Border)null);
        this.setCursor(new Cursor(12));
        this.setIcon(new ImageIcon(this.getClass().getResource("emoji/" + icon)));

        this.setName(icon);
    }
}
