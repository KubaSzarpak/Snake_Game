package Buttons;

import Game.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToMenuButton extends JButton implements ActionListener {
    GameFrame frame;
    public ToMenuButton(GameFrame frame){
        this.frame = frame;
        this.addActionListener(this);
        this.setFont(new Font("MV Boli", Font.BOLD, 30));
        this.setText("Menu");
        this.setBackground(Color.WHITE);
        this.setForeground(Color.darkGray);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.addGameMenu();
    }
}
