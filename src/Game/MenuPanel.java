package Game;

import Buttons.ExitButton;
import Buttons.PlayButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MenuPanel extends JPanel {
    static private final int SCREEN_WIDTH = 600;
    static private final int SCREEN_HEIGHT = 600;
    private int bestScore;
    private GameFrame frame;
    private BufferedImage image;

    MenuPanel(GameFrame frame){
        this.frame = frame;

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.darkGray);
        this.setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));

        PlayButton playButton = new PlayButton(frame);

        ExitButton exitButton = new ExitButton();

        buttonsPanel.add(playButton);
        buttonsPanel.add(exitButton);

        this.add(buttonsPanel, BorderLayout.SOUTH);

        try {
            image = ImageIO.read(new File("Snake_head.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        checkScores();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //Snake head image
        Graphics2D images2D = (Graphics2D) g;
        images2D.drawImage(image, 0, 0, null);

        //Snake text
        g.setColor(Color.white);
        g.setFont(new Font("Calibri", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Snake", (SCREEN_WIDTH - metrics1.stringWidth("Snake")) / 2, SCREEN_HEIGHT / 3);

        //Best score text
        g.setFont(new Font("Calibri", Font.BOLD, 30));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Best score: " + bestScore, (SCREEN_WIDTH - metrics2.stringWidth("Best score: " + bestScore)) / 2, SCREEN_HEIGHT / 2);


    }

    public void checkScores(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Scores.txt"));
            String text;

            while((text = reader.readLine()) != null){

                int temp = 0;
                for(int i = 0; i < text.length(); i++){
                        temp += (text.charAt(i) - '0') * (Math.pow(10, (text.length() - (i + 1))));
                }

                if(temp > bestScore)
                    bestScore = temp;
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
