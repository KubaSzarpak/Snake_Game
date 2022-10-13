package Buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitButton extends JButton implements ActionListener {
    public ExitButton(){
        this.addActionListener(this);
        this.setFont(new Font("MV Boli", Font.BOLD, 30));
        this.setText("Exit");
        this.setBackground(Color.WHITE);
        this.setForeground(Color.darkGray);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
