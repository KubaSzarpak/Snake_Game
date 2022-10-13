package Game;

import Buttons.ExitButton;
import Buttons.PlayButton;
import Buttons.ToMenuButton;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel{
    static private final int SCREEN_WIDTH = 600;
    static private final int SCREEN_HEIGHT = 600;
    private GameFrame frame;

    GameOverPanel(GameFrame frame){
        this.frame = frame;

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.darkGray);
        this.setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1,3));

        ToMenuButton toMenuButton = new ToMenuButton(frame);

        PlayButton playButton = new PlayButton(frame);

        ExitButton exitButton = new ExitButton();

        buttonsPanel.add(toMenuButton);
        buttonsPanel.add(playButton);
        buttonsPanel.add(exitButton);

        this.add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //game over text
        g.setColor(Color.white);
        g.setFont(new Font("Calibri", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics1.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 3);
        //your score text
        g.setFont(new Font("Calibri", Font.BOLD, 30));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Your score: " + frame.score, (SCREEN_WIDTH - metrics2.stringWidth("Your score: " + frame.score)) / 2, SCREEN_HEIGHT / 2);
    }

}
