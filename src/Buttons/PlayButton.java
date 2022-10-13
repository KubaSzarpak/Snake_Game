package Buttons;

import Game.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayButton extends JButton implements ActionListener {
    GameFrame frame;
    public PlayButton(GameFrame frame){
        this.frame = frame;

        this.addActionListener(this);
        this.setFont(new Font("MV Boli", Font.BOLD, 30));
        this.setText("Play");
        this.setBackground(Color.WHITE);
        this.setForeground(Color.darkGray);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.addGamePanel();
    }
}
